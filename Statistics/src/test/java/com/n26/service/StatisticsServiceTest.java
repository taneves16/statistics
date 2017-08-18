package com.n26.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.n26.business.StatisticsManager;
import com.n26.entity.Summary;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;

@RunWith(PowerMockRunner.class)
public class StatisticsServiceTest {
    
    private StatisticsService statisticsService;
    private StatisticsManager statisticsManager;
    
    @Before
    public void setUp() throws InternalErrorException {
    	statisticsManager = Mockito.mock(StatisticsManager.class);
    	Mockito.when(statisticsManager.getLogger()).thenReturn(Logger.getLogger(StatisticsServiceTest.class));
    	statisticsService = new StatisticsService();
        statisticsService.setStatisticsManager(statisticsManager);
        statisticsService.setLogger(statisticsManager.getLogger());
    }
    
    @Test
    public void testInternalError() throws InternalErrorException{
        Mockito.when(statisticsManager.calculateSummary()).thenThrow(new InternalErrorException("Some error occured while processing"));
        com.n26.dto.Response response = statisticsService.calculateSummary();
        assertEquals(response.getCode(), "500");
    }
    
    @Test
    public void testOldTimeStamp() throws InternalErrorException, InvalidTimeStampException{
    	Summary summaryBefore = statisticsManager.calculateSummary();
    	statisticsManager.calculateStatistics(new Transaction(14.0, System.currentTimeMillis() - 60000));
        com.n26.dto.Response response = statisticsService.calculateSummary();
        assertEquals(response.getData(), summaryBefore);
    }

    @Test
    public void testCalculateSummary() throws InternalErrorException, InvalidTimeStampException{
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
    	
    	Summary summary = new Summary();
    	summary.setCount(10L);
    	summary.setMax(65.5);
    	summary.setMin(2.8);
    	summary.setAvg(15.13);
    	summary.setSum(151.3);
    	when(statisticsManager.calculateSummary()).thenReturn(summary);
        com.n26.dto.Response response = statisticsService.calculateSummary();
        assertEquals(response.getData().toString(), "Summary [sum=151.3, avg=15.13, max=65.5, min=2.8, count=10]");
    }
}
