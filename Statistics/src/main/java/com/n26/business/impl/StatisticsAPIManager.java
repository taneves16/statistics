package com.n26.business.impl;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.n26.business.StatisticsManager;
import com.n26.entity.Statistics;
import com.n26.entity.Summary;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;

public class StatisticsAPIManager implements StatisticsManager {
	
	private static Logger logger;
	private static final int SECS = 60;
    private static final Map<Integer, Statistics> statsMap = new ConcurrentHashMap<>(SECS);
	
	public StatisticsAPIManager() {
		this.logger = Logger.getLogger(StatisticsAPIManager.class);
	}

	@Override
	public Logger getLogger() {
		return this.logger;
	}

	@Override
	public void calculateStatistics(Transaction transaction) throws InvalidTimeStampException, InternalErrorException {
		try{
			ZonedDateTime zdt = Instant.now().atZone(ZoneOffset.UTC);
			long currentTime = zdt.toInstant().toEpochMilli();
			 if ((currentTime - transaction.getTimestamp()) / 1000 < SECS) {
		            int sec = ZonedDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneOffset.UTC).getSecond();
		            statsMap.compute(sec, (entry, stat) -> {
		                if (null == stat || (currentTime - stat.getTimestamp())/1000 >= SECS) {
		                    stat = new Statistics();
		                    stat.setTimestamp(transaction.getTimestamp());
		                    stat.setSum(transaction.getAmount());
		                    stat.setMax(transaction.getAmount());
		                    stat.setMin(transaction.getAmount());
		                    stat.setCount(1l);
		                    return stat;
		                }
	
		                stat.setCount(stat.getCount() + 1);
		                stat.setSum(stat.getSum() + transaction.getAmount());
		                if (Double.compare(transaction.getAmount(), stat.getMax()) > 0) stat.setMax(transaction.getAmount());
		                if (Double.compare(transaction.getAmount(), stat.getMin()) < 0) stat.setMin(transaction.getAmount());
		                return stat;
		            });
		        }else
		        	throw new InvalidTimeStampException("Timestamp is older than 60 secs!");
		}catch(InvalidTimeStampException e) {
		    throw e;	
		}catch(Exception e){
			throw new InternalErrorException("Some error occured while processing");
		}
		
	}

	@Override
	public Summary calculateSummary() throws InternalErrorException {
		
		try{
			ZonedDateTime zdt = Instant.now().atZone(ZoneOffset.UTC);
			long currentTime = zdt.toInstant().toEpochMilli();
			
			Summary summary = statsMap.values().stream()
	                .filter(s -> (currentTime - s.getTimestamp()) / 1000 < SECS)
	                .map(Summary::new)
	                .reduce(new Summary(), (sum1, sum2) -> {
	                    sum1.setSum(sum1.getSum() + sum2.getSum());
	                    sum1.setCount(sum1.getCount() + sum2.getCount());
	                    sum1.setMax(Double.compare(sum1.getMax(), sum2.getMax()) > 0 ? sum1.getMax() : sum2.getMax());
	                    sum1.setMin(Double.compare(sum1.getMin(), sum2.getMin()) < 0 ? sum1.getMin() : sum2.getMin());
	                    return sum1;
	                });
	
	        summary.setMin(Double.compare(summary.getMin(), Double.MAX_VALUE) == 0 ? 0.0 : summary.getMin());
	        summary.setMax(Double.compare(summary.getMax(), Double.MIN_VALUE) == 0 ? 0.0 : summary.getMax());
	        summary.setAvg(summary.getCount() > 0l ? summary.getSum() / summary.getCount() : 0.0);
	
	        return summary;
		}catch(Exception e){
			throw new InternalErrorException("Some error occured while processing");
		}
	}

}
