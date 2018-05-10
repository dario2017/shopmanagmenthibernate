package com.cschool.shop.managment.shared.model;

import java.io.Serializable;

import com.cschool.shop.managment.client.viewshop.StaticFields;
import com.cschool.shop.managment.server.model.CategoryHibernate;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Category implements IsSerializable {
	private static int idCounter;
	private int categoryId;
	private String categoryName;
	
	public Category() {
	}
	
	public Category(String categoryName) {
		this.categoryName = categoryName;
	}
	
//	Contructor which manually sets Id
	public Category(int startId, String categoryName) {
		this.categoryId = startId;
		this.categoryName = categoryName;
	}
	


	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

	@Override
	public String toString() {
		return categoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
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
		Category other = (Category) obj;
		if (categoryId != other.categoryId)
			return false;
		return true;
	}




//	public CategoryHibernate convertToCategoryEntity() {
//		CategoryHibernate categoryHibernate = new CategoryHibernate();
//		categoryHibernate.setCategoryId(this.categoryId);
//		categoryHibernate.setCategoryName(this.categoryName);
//		return categoryHibernate;
//	}
	
	
}
