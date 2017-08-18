package com.n26.business;

import com.n26.business.impl.StatisticsAPIManager;
import com.n26.business.impl.TransactionAPIManager;

/**
 * Singleton class serves as a manager, holding all necessary business layer manager instances. It gets initialized at startup.
 * 
 * @author Tanya
 */
public enum BusinessManager {
    INSTANCE;

    private TransactionManager transactionManager;
    private StatisticsManager statisticsManager;
    
    /**
     * /**
     * Initializes BusinessManager, instantiates different business layer manager classes.
     * 
     * @param logger
     */
    public void init(){
    	statisticsManager = new StatisticsAPIManager();
        transactionManager = new TransactionAPIManager(statisticsManager);
    }
    
    /**
     * @return TransactionManager
     */
    public TransactionManager getTransactionManager(){
        return transactionManager;
    }
    
    /**
     * @return StatisticsManager
     */
    public StatisticsManager getStatisticsManager(){
        return statisticsManager;
    }
        
}
