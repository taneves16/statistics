package com.n26.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.n26.business.BusinessManager;
import com.n26.business.TransactionManager;
import com.n26.dto.Response;
import com.n26.dto.Status;
import com.n26.entity.Transaction;
import com.n26.exception.InternalErrorException;
import com.n26.exception.InvalidTimeStampException;
import com.n26.exception.ValidationManagerException;

import org.apache.log4j.Logger;

/**
 *  API Service which can be used to record a transaction
 *  
 * @author Tanya
 *
 */

@Path("/transactions")
@Produces({ MediaType.APPLICATION_JSON })
public class TransactionService {

    private TransactionManager transactionManager;
    private static Logger logger;
    
    /**
     * Constructor - initializes TransactionManager and Logger
     * */
    public TransactionService() {
        setTransactionManager(BusinessManager.INSTANCE.getTransactionManager());
        setLogger(Logger.getLogger(TransactionService.class));
    }
    
	    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response recordTransaction(@QueryParam("transaction") Transaction transaction) {
     try {
    	 transactionManager.addTransaction(transaction);
         return new Response(Status.CREATED.getCode());
     } catch (InvalidTimeStampException e) {
         logger.error("Service class recordTransaction() - Old Transaction 204" );
         return new Response(Status.OLD_TRANSACTION.getCode());
     } catch (ValidationManagerException e){
    	 logger.error("Service class recordTransaction() - Bad Request 400" );
         return new Response(Status.BAD_REQUEST.getCode());
     } catch (InternalErrorException e) {
    	 logger.error("Service class recordTransaction() - Server Error 500" );
         return new Response(Status.SERVER_ERROR.getCode());
     } 
    }
	
	/**
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}