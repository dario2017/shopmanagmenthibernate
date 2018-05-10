package com.cschool.shop.managment.client.service;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("category")
public interface CategoryServiceRPC extends RemoteService {
	boolean addCategory(Category category);
	
	boolean removeCategory(Category category);
	
	Set<Category> getAllCategories();
}
