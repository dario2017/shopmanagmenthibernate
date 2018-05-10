package com.cschool.shop.managment.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cschool.shop.managment.client.viewshop.StaticFields;
import com.cschool.shop.managment.shared.model.Category;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "category_table")
public class CategoryHibernate {
	private int categoryId;
	private String categoryName;
	private Set<ProductHibernate> productSet;
	
	public CategoryHibernate() {
		productSet = new HashSet<>();
	}
	
	public CategoryHibernate(String categoryName) {
		productSet = new HashSet<>();
		this.categoryName = categoryName;
	}
	
//	Contructor which manually sets Id
	public CategoryHibernate(int startId, String categoryName) {
		productSet = new HashSet<>();
		this.categoryId = startId;
		this.categoryName = categoryName;
	}
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	@Column(name = "category_name")
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String toString() {
		return categoryName;
	}
	
	@ManyToMany(mappedBy = "categorySet")
	public Set<ProductHibernate> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<ProductHibernate> productSet) {
		this.productSet = productSet;
	}
	
	

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
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
		CategoryHibernate other = (CategoryHibernate) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		return true;
	}

	public Category convertToCategory() {
		Category category = new Category();
		category.setCategoryId(this.categoryId);
		category.setCategoryName(this.categoryName);
		return category;
	}
	

}
