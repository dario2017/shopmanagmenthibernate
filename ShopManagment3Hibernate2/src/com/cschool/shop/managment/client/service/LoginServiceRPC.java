package com.cschool.shop.managment.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginServiceRPC extends RemoteService {
	boolean login(String login, char[] password);
}
