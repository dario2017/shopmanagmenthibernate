package com.cschool.shop.managment.server.dao;

import com.cschool.shop.managment.server.model.User;

public interface UserDao {
	void add(User user);
	
	void remove(long id);	
	
	void update(User user);
	
	User findById(long id);
	
	User findByLoginAndPassword(String login, char[] password);
	
}
