package com.cschool.shop.managment.server.service;

import com.cschool.shop.managment.server.dao.UserDao;
import com.cschool.shop.managment.server.model.User;
import com.cschool.shop.managment.server.session.SessionManager;

public class UserServiceImpl extends AbstractService implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(SessionManager manager, UserDao userDao) {
		super(manager);
		
		if (userDao == null) {
			throw new IllegalArgumentException("UserDao cannot be null");
		}	
		this.userDao = userDao;
	}
	
	@Override
	public void add(User user) {
		this.userDao.add(user);
		
	}

	@Override
	public void remove(long id) {
		this.userDao.remove(id);		
	}

	@Override
	public void update(User user) {
		this.userDao.update(user);		
	}

	@Override
	public User findById(long id) {
		return this.userDao.findById(id);
	}

	@Override
	public User findByLoginAndPassword(String login, char[] password) {
		return doInTransaction(() -> userDao.findByLoginAndPassword(login, password));
//		return this.userDao.findByLoginAndPassword(login, password);
	}

}
