package com.n26.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.n26.business.BusinessManager;
import com.n26.business.StatisticsManager;
import com.n26.dto.Response;
import com.n26.dto.Status;
import com.n26.exception.InternalErrorException;

/**
 * API Service which can be used to get a summary of transaction for the last 60 secs
 * 
 * @author Tanya
 *
 */

@Path("/statistics")
@Produces({ MediaType.APPLICATION_JSON })
public class StatisticsService {

	private StatisticsManager statisticsManager;
    private static Logger logger;
    
    /**
     * Constructor - initializes TransactionManager and Logger
     * */
    public StatisticsService() {
        setStatisticsManager(BusinessManager.INSTANCE.getStatisticsManager());
        setLogger(Logger.getLogger(StatisticsService.class));
    }
    
    @GET
    public Response calculateSummary() {
     try {
         return new Response(Status.SUCCESS.getCode(),statisticsManager.calculateSummary());
     } catch (InternalErrorException e) {
         return new Response(Status.SERVER_ERROR.getCode());
     } 
    }
    
    /**
     * @param statisticsManager the statisticsManager to set
     */
    public void setStatisticsManager(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
    }
    
    /**
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
