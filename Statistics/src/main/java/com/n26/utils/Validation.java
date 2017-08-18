package com.n26.utils;

import com.n26.entity.Transaction;
import com.n26.exception.ValidationManagerException;

public class Validation {
	
	public static void validateTransaction(Transaction transaction) throws ValidationManagerException{
		if(null == transaction || null == transaction.getAmount() || null == transaction.getTimestamp()
				|| transaction.getTimestamp() <= 0 || transaction.getAmount() < 0){
			throw new ValidationManagerException("Error with the paramaters passed");
		}
	}

}
