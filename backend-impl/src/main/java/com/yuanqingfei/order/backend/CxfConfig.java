package com.yuanqingfei.order.backend;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.yuanqingfei.order.backend.security.BasicAuthenticationProvider;
import com.yuanqingfei.order.backend.security.ServerPasswordCallbackHandler;
import com.yuanqingfei.order.backend.security.WSConfigProperty;
import com.yuanqingfei.order.backend.soap.BackendOrderServiceImpl;

@Configuration
@PropertySource({ "classpath:prop/wsConf.properties" })
public class CxfConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${ws.userName}")
	public String userName;

	@Value("${ws.password}")
	public String password;
	
	
	@Configuration
	@EnableWebSecurity
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Bean
		public AuthenticationProvider authenticationProvider() {
			return new BasicAuthenticationProvider();
		}
		
//		@Override
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//			// @formatter:off
//			auth.inMemoryAuthentication()
//				.withUser("roy")
//					.password("spring")
//					.roles("USER");
//			// @formatter:on
//		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authenticationProvider(authenticationProvider()).csrf().disable().authorizeRequests().anyRequest()
					.authenticated().and().httpBasic();
		}
	}	


	// <bean id="cxf" class="org.apache.cxf.bus.spring.SpringBus">
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// <jaxws:endpoint id="backendOrderService"
	// implementor="#backendOrderServiceImpl"
	// address="/backendOrderService">
	// </jaxws:endpoint>
	@Bean
	public Endpoint server() {
		Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
		Object implementor = new BackendOrderServiceImpl();
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);

		// endpoint.getInInterceptors().add(inInterceptor());

		endpoint.publish("/OrderService");
		return endpoint;
	}

	public WSS4JInInterceptor inInterceptor() {
		WSS4JInInterceptor ic = new WSS4JInInterceptor();

		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, getHandler());

		ic.setProperties(outProps);
		return ic;
	}

	@Bean
	public ServerPasswordCallbackHandler getHandler() {
		return new ServerPasswordCallbackHandler();
	}

	@Bean(name = "wsconf")
	public WSConfigProperty getWSConf() {
		WSConfigProperty conf = new WSConfigProperty();
		conf.setPassword(password);
		conf.setUserName(userName);
		return conf;
	}

}
