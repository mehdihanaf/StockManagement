package com.stock.services.impl;

import com.stock.model.UserDTO;
import com.stock.repository.IUserRepository;
import com.stock.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService<UserDTO, Long> {

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDTO get(Long id) {
        log.debug("Retrieve User with id {}", id);
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID !"));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getByUsername(String username) {
        log.debug("Retrieve User with username {}", username);
        var user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User Not found !"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String getUserRolesByUsername(String username) {
        var userDto = getByUsername(username);
        return userDto.getRoles();
    }

    @Override
    public void logged(String username, String token) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User Not found !"));
        user.setLogged(true);
        user.setToken(token);
        userRepository.saveAndFlush(user);
    }


}
