package com.cschool.shop.managment.server.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.server.dao.jdbc.CategoryDaoDB;
import com.cschool.shop.managment.shared.model.Category;

public class SetCategoryDao implements CategoryDao {
	
//	private Set<Category> categorySet;
	
	
	public SetCategoryDao() {
//		categorySet = new HashSet<>();
	}
	@Override
	public Set<Category> getCategorySet() {
		CategoryDaoDB categoryDaoDb = new CategoryDaoDB();
		return categoryDaoDb.getCategoryDB();
	}

	@Override
	public void addCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Cannot add null category");
		}
		CategoryDaoDB categoryDaoDb = new CategoryDaoDB();
		categoryDaoDb.addCategoryToDB(category);
//		categorySet.add(category);
	}
	
	@Override
	public void removeCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Cannot remove null category");
		}
		CategoryDaoDB categoryDaoDb = new CategoryDaoDB();
		categoryDaoDb.removeCategory(category);
//		categorySet.remove(category);
	}

}
