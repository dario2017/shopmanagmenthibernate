package com.cschool.shop.managment.server.service;

import org.hibernate.Transaction;

import com.cschool.shop.managment.server.session.SessionManager;


public abstract class AbstractService {
	protected final SessionManager manager;
	
	public AbstractService(SessionManager manager) {
		this.manager = manager;
	}
	
	protected <T> T doInTransaction(TransactionAction<T> action) {
		boolean transactionOwner = false;
		
		Transaction transaction = manager.getCurrentTransaction();
		if(transaction == null) {
			transaction = manager.beginTransaction();
			transactionOwner = true;
		}
		
		try {
			T result = action.execute();
			
			if(transactionOwner) {
				manager.commitTransaction();	
			}		
			
			return result;
		} catch (Exception ex) {
			if(transactionOwner) {
				manager.rollbackTransaction();
			}
			
			throw new IllegalStateException("Transaction should be rolled back", ex);
		}		
	}
	
	@FunctionalInterface
	public static interface TransactionAction<T> {
		T execute();
	}
}
