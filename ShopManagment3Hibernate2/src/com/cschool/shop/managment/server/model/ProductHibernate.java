package com.cschool.shop.managment.server.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cschool.shop.managment.client.viewshop.StaticFields;
import com.cschool.shop.managment.shared.exception.EmptyFieldInProductConstructorException;
import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "product_table")
public class ProductHibernate implements IsSerializable {
	private int productId;
	private String name;
	private Set<CategoryHibernate> categorySet;
	private double price;
	private boolean available;
	private String image;
	
	public ProductHibernate() {
		categorySet = new HashSet<>();
	}
	
	public ProductHibernate(String name, Set<CategoryHibernate> categorySet, double price, boolean available, String image) {		
		categorySet = new HashSet<>();
		this.name = name;
		this.categorySet = categorySet;
		this.price = price;
		this.available = available;
		this.image = image;
	}

//	Contructor which manually sets Id
	public ProductHibernate(int startId, String name, Set<CategoryHibernate> categorySet, double price, boolean available, String image) {		
		categorySet = new HashSet<>();
		this.productId = startId;
		this.name = name;
		this.categorySet = categorySet;
		this.price = price;
		this.available = available;
		this.image = image;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	@Column(name = "product_name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "product_category",
			joinColumns = {
					@JoinColumn(name = "product_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "category_id")
			}
		)
	public Set<CategoryHibernate> getCategorySet() {
		return categorySet;
	}
	
	public void addCategories(CategoryHibernate category) {
		this.categorySet.add(category);
		
	}

	public void setCategorySet(Set<CategoryHibernate> productsSet) {
		this.categorySet = productsSet;
	}

	@Column(name = "product_price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "product_availability")
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Column(name = "product_image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
//	
//	public Product convertToProduct() {
//		Product product = new Product();
//		product.setProductId(this.getProductId());
//		product.setAvailable(this.isAvailable());
//		product.setCategorySet(this.produ);
//		Category category = new Category();
//		category.setCategoryId(this.categoryId);
//		category.setCategoryName(this.categoryName);
//		return category;
//	}
	

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
		ProductHibernate other = (ProductHibernate) obj;
		if (productId != other.productId)
			return false;
		return true;
	}
	
	
	
	
}
