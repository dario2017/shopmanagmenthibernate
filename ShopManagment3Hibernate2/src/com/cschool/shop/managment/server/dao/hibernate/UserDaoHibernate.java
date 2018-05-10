package com.cschool.shop.managment.server.dao.hibernate;

import java.util.Arrays;

import org.hibernate.query.Query;

import com.cschool.shop.managment.server.dao.UserDao;
import com.cschool.shop.managment.server.model.User;
import com.cschool.shop.managment.server.session.SessionManager;

public class UserDaoHibernate implements UserDao {
	
	private final SessionManager manager;
	
	
	public UserDaoHibernate(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByLoginAndPassword(String login, char[] password) {
		Query<User> query = manager.getSession()
				.createQuery("from User user WHERE user.login = :loginParam AND user.password = :passwordParam", User.class);
		query.setParameter("loginParam", login);
		query.setParameter("passwordParam", password);
		if (!(query.list().isEmpty())) {
			User user = query.list().get(0);
			if (user.getLogin().equals(login) && Arrays.equals(user.getPassword(), password)) {
				return user;
			}
		}
		System.out.println("LOGOWANIE!!!!");
		return null;
	}
	
}
