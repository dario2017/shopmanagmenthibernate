package com.cschool.shop.managment;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Set;

import org.junit.Test;

import com.cschool.shop.managment.server.dao.hibernate.CategorySetDaoHibernate;
import com.cschool.shop.managment.server.model.CategoryHibernate;
import com.cschool.shop.managment.server.session.SessionManager;

public class Testy {
	
	@Test
	public void test() {
		System.out.println("dg");
		assertThat(23.0, is(23.0));
	}
	
	@Test
	public void testConnection() {
		SessionManager manager = new SessionManager();
		CategorySetDaoHibernate categoryDao = new CategorySetDaoHibernate(manager);
//		Set<CategoryHibernate> categorySet = categoryDao.getCategorySet();
//		System.out.println(categorySet.toString());
		
	}
}
