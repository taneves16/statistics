package com.n26.business.impl;

import org.apache.log4j.Logger;

import com.n26.business.StatisticsManager;
import com.n26.business.TransactionManager;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;
import com.n26.exception.ValidationManagerException;
import com.n26.utils.Validation;

public class TransactionAPIManager implements TransactionManager{
	
	private static Logger logger;
	private StatisticsManager statisticsManager;
	
	public TransactionAPIManager(StatisticsManager statisticsManager) {
		this.logger = Logger.getLogger(TransactionAPIManager.class);
		this.statisticsManager = statisticsManager;
	}

    @Override
    public void addTransaction(Transaction transaction) throws InvalidTimeStampException, InternalErrorException, ValidationManagerException {

    	Validation.validateTransaction(transaction);
    	statisticsManager.calculateStatistics(transaction);
    }

	@Override
	public Logger getLogger() {
		return this.logger;
	}
    

}
