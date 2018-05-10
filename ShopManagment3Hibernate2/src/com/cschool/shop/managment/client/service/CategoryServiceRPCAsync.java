package com.cschool.shop.managment.client.service;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CategoryServiceRPCAsync {

	void addCategory(Category category, AsyncCallback<Boolean> callback);
	
	void removeCategory(Category category, AsyncCallback<Boolean> callback);
	
	void getAllCategories(AsyncCallback<Set<Category>> callback);
	
}
