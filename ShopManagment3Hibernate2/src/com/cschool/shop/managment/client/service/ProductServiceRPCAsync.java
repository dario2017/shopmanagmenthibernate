package com.cschool.shop.managment.client.service;

import java.util.List;

import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceRPCAsync {

	void addProduct(Product product, AsyncCallback<Boolean> callback);
	
	void removeProduct(Product product, AsyncCallback<Boolean> callback);

	void updateProduct(Product product, AsyncCallback<Boolean> callback);
	
	void getAllProducts(AsyncCallback<List<Product>> callback);

}
