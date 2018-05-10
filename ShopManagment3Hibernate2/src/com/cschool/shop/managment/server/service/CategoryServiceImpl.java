package com.cschool.shop.managment.server.service;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.server.dao.CategoryDao;
import com.cschool.shop.managment.server.session.SessionManager;
import com.cschool.shop.managment.shared.model.Category;

public class CategoryServiceImpl extends AbstractService implements CategoryService {

	private final CategoryDao categoryDao;
	
	public CategoryServiceImpl(SessionManager manager, CategoryDao categoryDao) {
		super(manager);
		
		if (categoryDao == null) {
			throw new IllegalArgumentException("Cannot add null categoryDao");
		}
		this.categoryDao = categoryDao;
	}
	
	@Override
	public void addCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Cannot add null category");
		}
		doInTransaction(() -> {	
			categoryDao.addCategory(category);
		
			return null;
		});		
//		this.categoryDao.addCategory(category);	
	}

	@Override
	public void removeCategory(Category category) {
		doInTransaction(() -> {
			categoryDao.removeCategory(category);
			
			return null;
		});
//		this.categoryDao.removeCategory(category);
	}
	@Override
	public Set<Category> getCategorySet() {
		return doInTransaction(() -> categoryDao.getCategorySet());
//		return this.categoryDao.getCategorySet();
	}

}
