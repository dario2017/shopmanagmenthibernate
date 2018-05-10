package com.cschool.shop.managment.server.servlet.service;

import java.util.List;

import com.cschool.shop.managment.client.service.ProductServiceRPC;
import com.cschool.shop.managment.server.listener.ApplicationStartupListener;
import com.cschool.shop.managment.server.service.ProductService;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServletRPCService extends RemoteServiceServlet implements ProductServiceRPC {

	@Override
	public boolean addProduct(Product product) {
		getProductService().add(product);
		return true;
	}
	
	@Override
	public boolean removeProduct(Product product) {
		getProductService().removeProd(product);
		return true;
	}
	
	@Override
	public boolean updateProduct(Product product) {
		getProductService().uptadeProd(product);
		return true;
	}

	@Override
	public List<Product> getAllProducts() {
		return getProductService().getProductList();
	}
	
	public ProductService getProductService() {
		return (ProductService) getServletContext().getAttribute(ApplicationStartupListener.PRODUCT_SERVICE_ATTRIBUTE);
		
	}

}
