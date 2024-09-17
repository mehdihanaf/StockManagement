package com.stock.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -7107879666540063217L;

	private final String message;

	public UserAuthenticationException(String message) {
		super();
		this.message = message;
		log.error(this.message);
	}

}
