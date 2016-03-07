package com.yuanqingfei.order.backend;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@Order(value=1)
public class WebInitializer implements WebApplicationInitializer {
	
	private static Logger logger = Logger.getLogger(WebInitializer.class);
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();  
        context.register(CxfConfig.class);
        
        Dynamic dispatcher = container.addServlet("CXFSoapServlet", CXFServlet.class);
        dispatcher.addMapping("/ws/*");
        dispatcher.setLoadOnStartup(1);
        
     // for serving up asynchronous events in tomcat
//        dispatcher.setAsyncSupported(true);
        
        container.addListener(new ContextLoaderListener(context));
//        logger.info("CXF Servlet for Soap completed");
        System.out.println("CXF Servlet for Soap completed");

        
//        Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(appContext));
//        dispatcher.addMapping("/");
//        dispatcher.setLoadOnStartup(1);
        
//        appContext.setConfigLocation("com.mycompany.webclient.config");
//        appContext.setServletContext(container);
//        container.addListener(new ContextLoaderListener(appContext)); 
//        container.addListener(new MyCompanyContextListener());
        
        
        container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
            .addMappingForUrlPatterns(null, false, "/*");


	}

}
