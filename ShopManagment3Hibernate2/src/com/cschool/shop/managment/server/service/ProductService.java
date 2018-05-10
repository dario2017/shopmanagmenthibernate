package com.cschool.shop.managment.server.service;

import java.util.List;

import com.cschool.shop.managment.shared.model.Product;

public interface ProductService {
	void add(Product product);
	
	void removeProd(Product product);
	
	void uptadeProd(Product product);
	
	List<Product> getProductList();
	
}
