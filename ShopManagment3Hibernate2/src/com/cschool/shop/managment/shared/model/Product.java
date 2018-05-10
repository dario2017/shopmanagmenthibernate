package com.cschool.shop.managment.shared.model;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.cschool.shop.managment.client.viewshop.StaticFields;
import com.cschool.shop.managment.shared.exception.EmptyFieldInProductConstructorException;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Product implements IsSerializable {
	private static int counterId;
	private int productId;
	private String name;
	private Set<Category> categorySet;
	private double price;
	private boolean available;
	private String image;
	
	public Product() {
	}
	
	public Product(String name, Set<Category> categorySet, double price, boolean available, String image) {		
//		counterId++;
//		if (StaticFields.getProductsList().size() != 0) {
//			int highestId = 0;
//			for (Product each: StaticFields.getProductsList()) {
//				if (each.getProductId() > highestId) {
//					highestId = each.getProductId();
//				}			
//			}
//			this.productId = highestId + 1;
//		} else {
//			this.productId = counterId;
//		}
		this.name = name;
		this.categorySet = categorySet;
		this.price = price;
		this.available = available;
		this.image = image;
	}

//	Contructor which manually sets Id
	public Product(int startId, String name, Set<Category> categorySet, double price, boolean available, String image) {		
		this.productId = startId;
		this.name = name;
		this.categorySet = categorySet;
		this.price = price;
		this.available = available;
		this.image = image;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategorySet() {
		return categorySet;
	}

	public void setCategorySet(Set<Category> productsSet) {
		this.categorySet = productsSet;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", productsSet=" + categorySet + ", price="
				+ price + ", available=" + available + ", image=" + image + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId != other.productId)
			return false;
		return true;
	}
	
	
	
	
}
