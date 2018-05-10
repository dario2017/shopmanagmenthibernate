package com.cschool.shop.managment.server.service;

import com.cschool.shop.managment.server.model.User;

public interface UserService {
	void add(User user);
	
	void remove(long id);	
	
	void update(User user);
	
	User findById(long id);
	
	User findByLoginAndPassword(String login, char[] password);
	
}
