package com.cschool.shop.managment.server.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.query.Query;

import com.cschool.shop.managment.server.dao.ProductDao;
import com.cschool.shop.managment.server.model.CategoryHibernate;
import com.cschool.shop.managment.server.model.ProductHibernate;
import com.cschool.shop.managment.server.session.SessionManager;
import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;

public class ListProductDaoHibernate implements ProductDao {

	private final SessionManager manager;
	
	public ListProductDaoHibernate(SessionManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void add(Product product) {
		Set<CategoryHibernate> categorySetHib = new HashSet<>();
		ProductHibernate productHib = new ProductHibernate(product.getName(), categorySetHib, product.getPrice(), product.isAvailable(), product.getImage());
		for(Category each: product.getCategorySet()) {
			productHib.addCategories(new CategoryHibernate(each.getCategoryName()));
		}
		
		manager.getSession()
			.save(productHib);
	}

	@Override
	public void removeProd(Product product) {
		Query<ProductHibernate> query = manager.getSession()
				.createQuery("from ProductHibernate product WHERE product.productId = :idParam", ProductHibernate.class);
		query.setParameter("idParam", product.getProductId());
		ProductHibernate productToRemove = query.getSingleResult();
		
		manager.getSession()
			.delete(productToRemove);
		
	}

	@Override
	public void updateProd(Product product) {
		Query<ProductHibernate> query = manager.getSession()
				.createQuery("from ProductHibernate product WHERE product.productId = :idParam", ProductHibernate.class);
		query.setParameter("idParam", product.getProductId());
		ProductHibernate productToUpdate = query.getSingleResult();
		if (product.getProductId() == productToUpdate.getProductId()) {
			updateProductHibernate(product, productToUpdate);
		}
		
		manager.getSession()
			.update(productToUpdate);
	}
	
	private ProductHibernate updateProductHibernate(Product product, ProductHibernate productToUpdate) {
		productToUpdate.setAvailable(product.isAvailable());
		productToUpdate.setImage(product.getImage());
		productToUpdate.setName(product.getName());
		productToUpdate.setPrice(product.getPrice());	
		productToUpdate.getCategorySet().clear();
		for(Category each: product.getCategorySet()) {
			productToUpdate.addCategories(new CategoryHibernate(each.getCategoryName()));
		}
		return productToUpdate;
	}

	@Override
	public Product findById(int productId) {
		return null;
	}

	@Override
	public List<Product> getProductList() {
		Query<ProductHibernate> query = manager.getSession()
				.createQuery("from ProductHibernate product", ProductHibernate.class);
		
		return convertEntityToDto(query.list());
		
//	zapytanie w criteria
//		Criteria criteria = manager.getSession().createCriteria(ProductHibernate.class);
//		criteria.setFetchMode("categorySet", FetchMode.JOIN);
//		return convertEntityToDto(criteria.list());
	}
	
	private List<Product> convertEntityToDto(List<ProductHibernate> productsEntityList) {
		List<Product> productsListDto = new ArrayList<>();
		for (ProductHibernate each: productsEntityList) {
			Set<Category> categorySet = new HashSet<>();
			for (CategoryHibernate eachCategory: each.getCategorySet()) {
				categorySet.add(new Category(eachCategory.getCategoryId(), eachCategory.getCategoryName()));
			}
			productsListDto.add(new Product(each.getProductId(), each.getName(), categorySet, each.getPrice(), each.isAvailable(), each.getImage()));
		}
		return productsListDto;
	}

}
