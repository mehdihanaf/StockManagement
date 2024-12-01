package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.UserApi;
import com.stock.exceptions.UserAuthenticationException;
import com.stock.model.CommonResponse;
import com.stock.model.LoginPayload;
import com.stock.model.Token;
import com.stock.security.AuthService;
import com.stock.services.impl.JwtUserDetailsService;
import com.stock.services.impl.UserServiceImpl;
import com.stock.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class AuthController implements UserApi {

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserServiceImpl userServiceImpl;

    private final AuthService authService;

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public ResponseEntity<Token> login(@Valid @RequestBody LoginPayload loginPayload) {
        try {
            authenticate(loginPayload.getUsername(), loginPayload.getPassword());
        } catch (BadCredentialsException e) {
            throw new UserAuthenticationException("Bad Credentials Provided");
        } catch (LockedException e) {
            throw new UserAuthenticationException(
                    String.format("User [%s] is currently Disabled! Contact your Administrator.", loginPayload.getUsername()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        var userDetails = userDetailsService.loadUserByUsername(loginPayload.getUsername());
        var token = jwtTokenUtil.generateToken(userDetails);
        userServiceImpl.logged(loginPayload.getUsername(), token);
        var currentUser = userServiceImpl.getByUsername(loginPayload.getUsername());
        Token response = new Token().userId(currentUser.getId()).token(token).expiration(authService.getExipartion(token))
                .roles(userServiceImpl.getUserRolesByUsername(loginPayload.getUsername()))
                .firstname(currentUser.getFirstName()).lastname(currentUser.getLastName())
                .username(currentUser.getUsername());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = UserAuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleUserAuthenticationException(UserAuthenticationException ex) {
        return new CommonResponse().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
                .details(ex.getStackTrace().toString());
    }

}
