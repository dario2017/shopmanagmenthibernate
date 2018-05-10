package com.cschool.shop.managment.server.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;
import com.cschool.shop.managment.shared.model.Product;

public class ProductDaoDB {
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";
	private static final String URL = "jdbc:postgresql://localhost:5432/UserShopManagment";
	private Connection dataBase;
	
	private List<Product> productList;
	private Set<Category> categoriesSet;
	
	public void addProduct(Product product) {
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement prepStatement = dataBase.prepareStatement("INSERT INTO product_table VALUES(DEFAULT, ?, ?, ?, ?);");
			String name = product.getName();
			Double price = product.getPrice();
			Boolean availability = product.isAvailable();
			String image = product.getImage();
			
			prepStatement.setString(1, name);
			prepStatement.setDouble(2, price);
			prepStatement.setBoolean(3, availability);
			prepStatement.setString(4, image);
			
			prepStatement.executeUpdate();
			prepStatement.close();
			
//	DOZROBIENIA zablokowac mozliwosc tworzenia produktow o tych samych nazwach
			PreparedStatement prepStatement2 = dataBase.prepareCall("SELECT product_id FROM product_table WHERE product_name = ?;");
			prepStatement2.setString(1, name);
			ResultSet rs = prepStatement2.executeQuery();
			int productId = -1;
			while (rs.next()) {
				productId = rs.getInt(1);
				product.setProductId(productId);
			}
			prepStatement2.close();
			
			Set<Category> categorySet = product.getCategorySet();
			for (Category each: categorySet) {
				PreparedStatement prepStatementX = dataBase.prepareStatement("INSERT INTO product_category VALUES(?, ?);");
				prepStatementX.setInt(1, productId);
				prepStatementX.setInt(2, each.getCategoryId());
				prepStatementX.executeUpdate();
				prepStatementX.close();
			}
			
			dataBase.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void removeProduct(Product product) {
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			
			PreparedStatement prepStatement = dataBase.prepareStatement("DELETE FROM product_table WHERE product_id = ?;");
			prepStatement.setInt(1, product.getProductId());
			prepStatement.executeUpdate();
			prepStatement.close();
			
			PreparedStatement ps2 = dataBase.prepareStatement("DELETE FROM product_category WHERE product_id = ?;");
			ps2.setInt(1, product.getProductId());
			ps2.executeUpdate();
			ps2.close();
			
			dataBase.close();			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateProduct(Product product) {
		
	}
	
	public List<Product> getProductList(){
		productList = new ArrayList<>();
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = dataBase.prepareStatement("SELECT * FROM product_table;");
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int productId = rs.getInt(1);
				String productName = rs.getString(2);
				Double productPrice = rs.getDouble(3);
				Boolean productAvailability = rs.getBoolean(4);
				String productImage = rs.getString(5);
				categoriesSet = getCategories(productId);
				
				productList.add(new Product(productId, productName, categoriesSet, productPrice, productAvailability, productImage));
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return productList; 
	}
	
	private Set<Category> getCategories(int productId) {
		Set<Category> catSet = new HashSet<>();
		try {
			PreparedStatement psCategory = dataBase.prepareCall("SELECT category_table.category_id, category_name FROM category_table "  
					 + "INNER JOIN product_category ON category_table.category_id = product_category.category_id " 
					 + "INNER JOIN product_table ON product_category.product_id = product_table.product_id " 
					 + "WHERE product_table.product_id = ?;");
			
			psCategory.setInt(1, productId);
			ResultSet rs = psCategory.executeQuery();
			while (rs.next()) {
				catSet.add(new Category(rs.getInt(1), rs.getString(2)));
			}
						
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return catSet;
	}
}
