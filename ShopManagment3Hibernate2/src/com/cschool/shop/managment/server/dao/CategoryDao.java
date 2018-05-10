package com.cschool.shop.managment.server.dao;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;

public interface CategoryDao {
	void addCategory(Category category);
	
	void removeCategory(Category category);
	
	Set<Category> getCategorySet();
}
