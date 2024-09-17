package com.stock.services;

import com.stock.security.UserPrincipal;
import com.stock.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

	private final UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var userDto = userService.getByUsername(username);
		UserPrincipal userPrincipal = null;
		if (userDto != null && (userDto.getDeleted() == null
				|| (userDto.getDeleted() != null && !userDto.getDeleted()))) {
			if (userDto.getActive()) {
				userPrincipal = new UserPrincipal(username, userDto.getPassword(), new ArrayList<>());
			} else {
				userPrincipal = new UserPrincipal(username, userDto.getPassword(), false, new ArrayList<>());
			}
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return userPrincipal;
	}

}
