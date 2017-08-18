package com.n26.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.n26.business.BusinessManager;


/**
 * Application Lifecycle Listener implementation class Startup, it reads parameters from the web.xml and initializes the api
 * 
 * @author Tanya
 */
@WebListener
public class Startup implements ServletContextListener {

    private static boolean isStartupSuccess = false;
    private static Logger logger = Logger.getLogger(Startup.class);

    /**
     *  ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {

        try {

            //  Initializing Business Manager
            BusinessManager.INSTANCE.init();
            logger.info("BusinessManager Initialized");

            isStartupSuccess = true;

        } catch (Exception e) {
            logger.fatal("Terminating application because of Exception. ");
        }
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    }

    /**
     * Returns whether startup was successful or failure.
     * 
     * @return {@link Boolean}
     * */
    public static boolean getStartupProcess() {
        return isStartupSuccess;
    }
}
