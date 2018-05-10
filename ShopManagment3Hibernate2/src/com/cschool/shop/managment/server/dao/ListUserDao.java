package com.cschool.shop.managment.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cschool.shop.managment.server.dao.exception.UserAlreadyExistException;
import com.cschool.shop.managment.server.dao.exception.UserDoesNotExistException;
import com.cschool.shop.managment.server.dao.jdbc.UserDaoDB;
import com.cschool.shop.managment.server.model.User;

public class ListUserDao implements UserDao {	
	
	public List<User> usersList = new ArrayList<>();
	
	@Override
	public void add(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		} else if (usersList.contains(user)) {
			throw new UserAlreadyExistException("User already exist in list!");
		}
		usersList.add(user);		
	}

	@Override
	public void remove(long id) {
		User user = null;
		for(User eachUser: usersList) {
			if (eachUser.getId() == id) {
				user = eachUser;
			}
		}
		if (user == null) {
			throw new IllegalArgumentException("There is no user with id: " + id);
		} else {
			usersList.remove(user);
		}
	}

	@Override
	public void update(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		
		User found = findById(user.getId());
		if (found == null) {
			add(user);
		} else {
			usersList.remove(found);
			usersList.add(user);
		}	
	}

	@Override
	public User findById(long id) {
		User user = null;
		for(User eachUser: usersList) {
			if (eachUser.getId() == id) {
				user = eachUser;
			}
		}
		return user;
	}

	@Override
	public User findByLoginAndPassword(String login, char[] password) {
		UserDaoDB userDao = new UserDaoDB();
		for (User eachUser: userDao.connectUserDB(login)) {
			if (eachUser.getLogin().equals(login) && Arrays.equals(eachUser.getPassword(), password)) {
				return eachUser;
			}
		}
		return null;
	}

}
