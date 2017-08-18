package com.n26.business;

import org.apache.log4j.Logger;

import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;
import com.n26.exception.ValidationManagerException;

/**
 * Interface providing business layer for Transaction resource
 * 
 * @author Tanya
 *
 */
public interface TransactionManager {

    /**
     * Records a transaction
     * 
     * @param transaction
     * @throws InvalidTimeStampException
     * @throws InternalErrorException
     */
    void addTransaction(Transaction transaction) throws InvalidTimeStampException,InternalErrorException,ValidationManagerException;
    
    /**
     * 
     * @returns Logger for the class
     */
    Logger getLogger();
}
