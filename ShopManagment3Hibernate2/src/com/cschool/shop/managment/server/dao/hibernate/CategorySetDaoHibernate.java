package com.cschool.shop.managment.server.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cschool.shop.managment.server.dao.CategoryDao;
import com.cschool.shop.managment.server.model.CategoryHibernate;
import com.cschool.shop.managment.server.session.SessionManager;
import com.cschool.shop.managment.shared.model.Category;


public class CategorySetDaoHibernate implements CategoryDao {

	private final SessionManager manager;
	
	public CategorySetDaoHibernate(SessionManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void addCategory(Category category) {
		CategoryHibernate categoryHib = new CategoryHibernate(category.getCategoryName());
//		Transaction tr = manager.getSession()
//			.beginTransaction();
		manager.getSession()
			.save(categoryHib);
//		tr.commit();
	}

	@Override
	public void removeCategory(Category category) {
		Query<CategoryHibernate> query = manager.getSession()
				.createQuery("from CategoryHibernate categoryHibernate WHERE categoryHibernate.categoryId = :idParam", CategoryHibernate.class);
		query.setParameter("idParam", category.getCategoryId());
		CategoryHibernate categoryHib = query.getSingleResult();

		manager.getSession()
			.delete(categoryHib);	
	}

	@Override
	public Set<Category> getCategorySet() {
		Query<CategoryHibernate> query = manager.getSession()
				.createQuery("from CategoryHibernate", CategoryHibernate.class);
		Set<CategoryHibernate> categoryEntitySet = new HashSet<>(query.list());
//		Set<Category> categorySet = convertEntityToDto(categoryEntitySet);
		Set<Category> categorySet = new HashSet<>();
		for (CategoryHibernate each: categoryEntitySet) {
			categorySet.add(each.convertToCategory());
		}
		return categorySet;
	}
	
//	private Set<Category> convertEntityToDto(Set<CategoryHibernate> categoryEntitySet) {
//		Set<Category> categorySet = new HashSet<>();
//		for (CategoryHibernate each: categoryEntitySet) {
//			categorySet.add(new Category(each.getCategoryId(), each.getCategoryName()));
//		}
//		return categorySet;
//	}

}
