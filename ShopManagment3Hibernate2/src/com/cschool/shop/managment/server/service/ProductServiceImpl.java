package com.cschool.shop.managment.server.service;

import java.util.List;

import com.cschool.shop.managment.server.dao.ProductDao;
import com.cschool.shop.managment.server.session.SessionManager;
import com.cschool.shop.managment.shared.model.Product;

public class ProductServiceImpl extends AbstractService implements ProductService {

	private final ProductDao productDao;

	public ProductServiceImpl(SessionManager manager, ProductDao productDao) {
		super(manager);
		
		if (productDao == null) {
			throw new IllegalArgumentException("ProductDao cannot be null");
		}	
		this.productDao = productDao;		
	}
	
	@Override
	public void add(Product product) {
		doInTransaction(() -> {
			productDao.add(product);
			return null;
		});
//		this.productDao.add(product);
	}
	
	@Override
	public void removeProd(Product product) {
		doInTransaction(() -> {
			productDao.removeProd(product);
			return null;
		});
//		this.productDao.removeProd(product);
	}
	
	@Override
	public void uptadeProd(Product product) {
		doInTransaction(() -> {
			productDao.updateProd(product);
			return null;
		});
//		this.productDao.updateProd(product);
	}

	@Override
	public List<Product> getProductList() {
		return doInTransaction(() -> productDao.getProductList());
//		return this.productDao.getProductList();
	}
	

}
