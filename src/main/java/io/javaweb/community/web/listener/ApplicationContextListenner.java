package io.javaweb.community.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationContextListenner implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListenner.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("ApplicationContextListenner contextDestroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("ApplicationContextListenner contextInitialized");
		ServletContext servletContext = servletContextEvent.getServletContext();
		//servletContext.setAttribute();

	}
}
