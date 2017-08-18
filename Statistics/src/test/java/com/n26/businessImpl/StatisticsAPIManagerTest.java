package com.n26.businessImpl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.n26.business.StatisticsManager;
import com.n26.business.impl.StatisticsAPIManager;
import com.n26.entity.Summary;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;

@RunWith(PowerMockRunner.class)
public class StatisticsAPIManagerTest {
	
	private StatisticsManager statisticsManager;
	
	@Before
    public void setUp() throws InternalErrorException {
		statisticsManager = new StatisticsAPIManager();
	}
	
	@Test
    public void testOldTimeStamp() throws InternalErrorException, InvalidTimeStampException{
    	Summary summaryBefore = statisticsManager.calculateSummary();
    	try{
    		statisticsManager.calculateStatistics(new Transaction(14.0, System.currentTimeMillis() - 60000));
    	}catch(InvalidTimeStampException e) {  		
    	}
        assertEquals(statisticsManager.calculateSummary(), summaryBefore);
    }

	@Test
    public void testValidTimeStamp() throws InternalErrorException, InvalidTimeStampException{
		statisticsManager.calculateStatistics(new Transaction(12.3, System.currentTimeMillis() - 1000));
    	statisticsManager.calculateStatistics(new Transaction(9.5, System.currentTimeMillis() - 2000));
    	statisticsManager.calculateStatistics(new Transaction(2.8, System.currentTimeMillis() - 3000));
    	statisticsManager.calculateStatistics(new Transaction(3.5, System.currentTimeMillis() - 4000));
    	statisticsManager.calculateStatistics(new Transaction(5.8, System.currentTimeMillis() - 5000));
    	statisticsManager.calculateStatistics(new Transaction(5.7, System.currentTimeMillis() - 6000));
    	statisticsManager.calculateStatistics(new Transaction(65.5, System.currentTimeMillis() - 7000));
    	statisticsManager.calculateStatistics(new Transaction(25.2, System.currentTimeMillis() - 8000));
    	statisticsManager.calculateStatistics(new Transaction(15.5, System.currentTimeMillis() - 9000));
    	statisticsManager.calculateStatistics(new Transaction(5.5, System.currentTimeMillis() - 10000));
		
    	Summary summary = statisticsManager.calculateSummary();
    	assertEquals(summary.getSum(), 151.3, 0.0);
    	assertEquals(summary.getAvg(), 15.130, 0.0);
        assertEquals(summary.getMax(), 65.5, 0.0);
        assertEquals(summary.getMin(), 2.8, 0.0);
        assertEquals(summary.getCount(), 10l);
    }
}
