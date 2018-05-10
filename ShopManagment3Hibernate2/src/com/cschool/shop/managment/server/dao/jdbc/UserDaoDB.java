package com.cschool.shop.managment.server.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cschool.shop.managment.server.model.User;

public class UserDaoDB {
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";
	private static final String URL = "jdbc:postgresql://localhost:5432/UserShopManagment";
	private Connection dataBase;
	
	private List<User> usersList;
	 
	public List<User> connectUserDB(String login) {
		usersList = new ArrayList<>();
	
		try {
			dataBase = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = dataBase.createStatement();

			PreparedStatement prepStatment = dataBase.prepareStatement("SELECT * FROM user_table WHERE user_name = ?;");
			prepStatment.setString(1, login);
			System.out.println("Login w database: " + login);
			
			ResultSet rs = prepStatment.executeQuery();
			
			while (rs.next()) {
				int userId = rs.getInt(1);
				String userName = rs.getString(2);	
				char[] userPassword = rs.getString(3).toCharArray();
				
				usersList.add(new User(userId, userName, userPassword));
			}
			
			statement.close();
			dataBase.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usersList;						
	}
}
