package com.stock.services.impl;

import com.stock.model.UserDTO;
import com.stock.models.User;
import com.stock.repository.IUserRepository;
import com.stock.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService<UserDTO, Long> {

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper;

    private static int MAX_PER_PAGE = 5;

    @Override
    public UserDTO get(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID !"));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User Not found !"));
        return modelMapper.map(user, UserDTO.class);
    }

    public String getUserRolesByEmail(String username) {
        UserDTO userDto = getByUsername(username);
        return userDto.getRoles();
    }

    public void logged(String username, String token) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User Not found !"));
        user.setLogged(true);
        user.setToken(token);
        userRepository.saveAndFlush(user);
    }


}
