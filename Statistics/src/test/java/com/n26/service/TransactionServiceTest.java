package com.n26.service;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.n26.business.TransactionManager;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;
import com.n26.exception.ValidationManagerException;

@RunWith(PowerMockRunner.class)
public class TransactionServiceTest {
    
    private TransactionService transactionService;
    private TransactionManager transactionManager;
    
    @Before
    public void setUp() throws InternalErrorException {
    	transactionManager = Mockito.mock(TransactionManager.class);
    	Mockito.when(transactionManager.getLogger()).thenReturn(Logger.getLogger(StatisticsServiceTest.class));
    	transactionService = new TransactionService();
    	transactionService.setTransactionManager(transactionManager);
    	transactionService.setLogger(transactionManager.getLogger());
    }
    
    @Test
    public void testNullTransaction() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
    	Transaction transaction = null;
    	Mockito.doThrow(new ValidationManagerException("Error with the paramaters passed")).when(transactionManager).addTransaction(transaction);
        com.n26.dto.Response response = transactionService.recordTransaction(transaction);
        assertEquals(response.getCode(), "400");
    }
    
    @Test
    public void testNullAmount() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
    	Transaction transaction = new Transaction(null, 0l);
    	Mockito.doThrow(new ValidationManagerException("Error with the paramaters passed")).when(transactionManager).addTransaction(transaction);
        com.n26.dto.Response response = transactionService.recordTransaction(transaction);
        assertEquals(response.getCode(), "400");
    }
    
    @Test
    public void testNullTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
    	Transaction transaction = new Transaction(1.0, null);
    	Mockito.doThrow(new ValidationManagerException("Error with the paramaters passed")).when(transactionManager).addTransaction(transaction);
        com.n26.dto.Response response = transactionService.recordTransaction(transaction);
        assertEquals(response.getCode(), "400");
    }
    
    @Test
    public void testOldTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
    	Transaction transaction = new Transaction(1.0, 300000L);
    	Mockito.doThrow(new InvalidTimeStampException("Timestamp is older than 60 secs!")).when(transactionManager).addTransaction(transaction);
        com.n26.dto.Response response = transactionService.recordTransaction(transaction);
        assertEquals(response.getCode(), "204");
    }
    
    @Test
    public void testValidTimeStamp() throws InternalErrorException, InvalidTimeStampException, ValidationManagerException{
    	Transaction transaction = new Transaction(1.0, 1235454L);
    	Mockito.doNothing().when(transactionManager).addTransaction(transaction);
        com.n26.dto.Response response = transactionService.recordTransaction(transaction);
        assertEquals(response.getCode(), "201");
    }

}

