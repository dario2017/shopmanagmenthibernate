package com.cschool.shop.managment.server.service;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;

public interface CategoryService {
	void addCategory(Category category);
	
	void removeCategory(Category category);
	
	Set<Category> getCategorySet();
}
