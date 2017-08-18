package com.n26.businessImpl;

import static org.mockito.Matchers.anyLong;

import org.junit.Before;
import org.junit.Test;

import com.n26.business.StatisticsManager;
import com.n26.business.TransactionManager;
import com.n26.business.impl.StatisticsAPIManager;
import com.n26.business.impl.TransactionAPIManager;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;
import com.n26.exception.ValidationManagerException;

public class TransactionAPIManagerTest {
	
private TransactionManager transactionManager;
private StatisticsManager statisticsManager;
	
	@Before
    public void setUp() throws InternalErrorException {
		statisticsManager = new StatisticsAPIManager();
		transactionManager = new TransactionAPIManager(statisticsManager);
	}
	
	@Test(expected=ValidationManagerException.class)
    public void testNullTransaction() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
		Transaction transaction = null;
		transactionManager.addTransaction(transaction);
	}
	
	@Test(expected=ValidationManagerException.class)
    public void testNullAmount() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
		Transaction transaction = new Transaction(null, anyLong());
		transactionManager.addTransaction(transaction);
	}
	
	@Test(expected=ValidationManagerException.class)
    public void testNullTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
		Transaction transaction = new Transaction(16.0, null);
		transactionManager.addTransaction(transaction);
	}
	
	@Test(expected=InvalidTimeStampException.class)
    public void testOldTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
		Transaction transaction = new Transaction(16.0, System.currentTimeMillis() - 60000);
		transactionManager.addTransaction(transaction);
	}
	
	@Test
    public void testValidTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
		Transaction transaction = new Transaction(16.0, System.currentTimeMillis() - 10000);
		transactionManager.addTransaction(transaction);
	}
}
