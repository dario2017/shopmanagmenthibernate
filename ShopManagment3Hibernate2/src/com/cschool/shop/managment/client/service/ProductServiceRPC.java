package com.cschool.shop.managment.client.service;

import java.util.List;

import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("product")
public interface ProductServiceRPC extends RemoteService {

	boolean addProduct(Product product);
	
	boolean removeProduct(Product product);
	
	boolean updateProduct(Product product);
	
	List<Product> getAllProducts();
}
