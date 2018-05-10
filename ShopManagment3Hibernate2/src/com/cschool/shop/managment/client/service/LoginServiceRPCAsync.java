package com.cschool.shop.managment.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceRPCAsync {
	
	void login(String login, char[] password, AsyncCallback<Boolean> callback);
}
