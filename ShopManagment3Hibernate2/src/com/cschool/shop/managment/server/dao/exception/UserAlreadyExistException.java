package com.cschool.shop.managment.server.dao.exception;

public class UserAlreadyExistException extends RuntimeException {

	public UserAlreadyExistException(String message) {
		super(message);
	}

}
