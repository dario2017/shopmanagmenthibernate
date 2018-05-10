package com.cschool.shop.managment.client.viewshop;

import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;

public class StaticFields {
	private static List<Product> productsList;
	private static Set<Category> categorySet;
	
	public static List<Product> getProductsList() {
		return productsList;
	}
	public static void setProductsList(List<Product> productsList) {
		StaticFields.productsList = productsList;
	}
	public static Set<Category> getCategorySet() {
		return categorySet;
	}
	public static void setCategorySet(Set<Category> categoryList) {
		StaticFields.categorySet = categoryList;
	}
	
}
