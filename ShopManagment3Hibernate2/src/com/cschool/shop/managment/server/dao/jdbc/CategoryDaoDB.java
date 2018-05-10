package com.cschool.shop.managment.server.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.cschool.shop.managment.shared.model.Category;

public class CategoryDaoDB {
	
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";
	private static final String URL = "jdbc:postgresql://localhost:5432/UserShopManagment";
	private Connection dataBase;
	
	private Set<Category> categorySet;
	
	public void addCategoryToDB(Category category) {
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			
			PreparedStatement prepStatement = dataBase.prepareStatement("INSERT INTO category_table VALUES(DEFAULT, ?);");
			String categoryName = category.getCategoryName();
			prepStatement.setString(1, categoryName);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
			PreparedStatement prepStatement2 = dataBase.prepareStatement("SELECT category_id FROM category_table WHERE category_name = ?");
			prepStatement2.setString(1, categoryName);
			ResultSet rs = prepStatement2.executeQuery();
			
			while (rs.next()) {
				category.setCategoryId(rs.getInt(1));
			}
			
			prepStatement2.close();
			dataBase.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	
	}
	
	public void removeCategory(Category category) {
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			
			PreparedStatement prepStatement = dataBase.prepareStatement("DELETE FROM category_table WHERE category_name = ?;");
			prepStatement.setString(1, category.getCategoryName());
			prepStatement.executeUpdate();
			
			prepStatement.close();
			dataBase.close();			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public Set<Category> getCategoryDB(){
		categorySet = new HashSet<>();
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			
			PreparedStatement prepStatement = dataBase.prepareStatement("SELECT * FROM category_table;");
			ResultSet rs = prepStatement.executeQuery();
			while (rs.next()) {
				int categoryId = rs.getInt(1);
				String categoryName = rs.getString(2);
				
				categorySet.add(new Category(categoryId, categoryName));
			}
			
			prepStatement.close();
			dataBase.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return categorySet;
	}
}
