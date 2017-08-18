package com.n26.business;

import org.apache.log4j.Logger;

import com.n26.entity.Statistics;
import com.n26.entity.Summary;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;

public interface StatisticsManager {
	
	/**
	 * Based on each transaction it calculates the statistics for the past one minute.
	 * This data is kept inside map and for each second an entry is created. The older 
	 * entry is updated with a new one.
     *
	 * @param transaction
	 * @throws InvalidTimeStampException 
	 * @throws InternalErrorException 
	 */
	void calculateStatistics(Transaction transaction) throws InvalidTimeStampException, InternalErrorException;
	
	/**
	 * It returns the final summary based on the stats map.
	 * 
	 * @return summary
	 */
	Summary calculateSummary() throws InternalErrorException;
	/**
     * 
     * @returns Logger for the class
     */
    Logger getLogger();

}
