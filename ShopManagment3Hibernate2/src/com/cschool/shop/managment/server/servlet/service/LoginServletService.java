package com.cschool.shop.managment.server.servlet.service;

import javax.servlet.ServletContext;

import com.cschool.shop.managment.client.service.LoginServiceRPC;
import com.cschool.shop.managment.server.dao.jdbc.UserDaoDB;
import com.cschool.shop.managment.server.listener.ApplicationStartupListener;
import com.cschool.shop.managment.server.model.User;
import com.cschool.shop.managment.server.service.UserService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServletService extends RemoteServiceServlet implements LoginServiceRPC {

	@Override
	public boolean login(String login, char[] password) {
//		UserDaoDB userDao = new UserDaoDB();
//		userDao.connectUserDB();
//		for (User each: userDao.connectUserDB()) {
//			getUserService().add(each);
//		}
		System.out.println("Login: " + login + " haslo: " + password.toString());
		return getUserService().findByLoginAndPassword(login, password) != null;
	}

	
	private UserService getUserService() {
		return (UserService) getServletContext().getAttribute(ApplicationStartupListener.USER_SERVICE_ATTRIBUTE);
	}
	
}
