package com.yuanqingfei.order.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import com.yuanqingfei.order.api.rest.OrderServiceImpl;
import com.yuanqingfei.order.api.security.ClientPasswordCallbackHandler;
import com.yuanqingfei.order.api.security.ConfigProperty;
import com.yuanqingfei.order.backend.soap.order.BackendOrderService;


@Configuration
@PropertySource({"classpath:ws.properties"})
public class CxfConfig {
	
	@Value("${ws.soap.address}")
	public String soapAddr;
	
	@Value("${ws.rest.address}")
	public String restAddr;	
	
	@Value("${ws.userName}")
	public String userName;
	
	@Value("${ws.password}")
	public String password;
	
	@Value("${ws.trustStore.password}")
	public String trustStorePassword;
	
	@Value("${ws.trustStore.fileName}")
	public String trustStoreFileName;
	

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}   	
	
	// <bean id="cxf" class="org.apache.cxf.bus.spring.SpringBus">
    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {    	
    	return new SpringBus();
    }
    
//	<jaxrs:server id="orderServiceServer" address="/rest/order">
//		<jaxrs:serviceBeans>
//			<ref bean="orderService" />
//		</jaxrs:serviceBeans>
//		<jaxrs:providers>
//			<bean class="org.codehaus.jackson.jaxrs.JacksonJsonProvider" />
//		</jaxrs:providers>
//	</jaxrs:server>
    @Bean
    public Server rsServer() {
    	Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setServiceBean(getOrderService());
        endpoint.setAddress(restAddr);
        endpoint.setBus(bus);
        
        List<Feature> featureList = new ArrayList<Feature>();
        Feature loggingFeature = new LoggingFeature();
        featureList.add(loggingFeature);
        endpoint.setFeatures(featureList);
        
        JacksonJsonProvider provider = new JacksonJsonProvider();
        endpoint.setProvider(provider);
        return endpoint.create();
    }  
    
    @Bean
    public BackendOrderService backendOrderService() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(BackendOrderService.class);
        factory.setAddress(soapAddr);
        factory.setFeatures(featureList());
        
//		factory.getOutInterceptors().add(outInterceptor());

    	BackendOrderService backendWS = (BackendOrderService)factory.create();
    	
    	Client client = ClientProxy.getClient(backendWS);
    	HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
		httpConduit.setClient(httpClientPolicy());
		httpConduit.setTlsClientParameters(tlsClientParameters());
		httpConduit.setAuthorization(authPolicy());
    	
		return backendWS;
    }  
    
    
    private AuthorizationPolicy authPolicy(){
    	AuthorizationPolicy authPolicy = new AuthorizationPolicy(); 
    	authPolicy.setAuthorizationType("Basic"); 
    	authPolicy.setUserName("username111"); 
    	authPolicy.setPassword("password111"); 
    	return authPolicy;
    }
    
    private List<Feature> featureList(){
        List<Feature> featureList = new ArrayList<Feature>();
        featureList.add(new LoggingFeature());
        return featureList;
    }
    
    public WSS4JOutInterceptor outInterceptor(){
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, userName);
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_REF, getClientPasswordCallbackHandler());
		return new WSS4JOutInterceptor(outProps);
    }
    
	public TLSClientParameters tlsClientParameters() {
		TLSClientParameters tlsParams = new TLSClientParameters();
		tlsParams.setDisableCNCheck(true);
		tlsParams.setSecureSocketProtocol("TLS");

		char[] passphrase = trustStorePassword.toCharArray();
		TrustManagerFactory tmf = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new ClassPathResource(trustStoreFileName).getInputStream(), passphrase);
			tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(keyStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.notNull(tmf);
		tlsParams.setTrustManagers(tmf.getTrustManagers());
		FiltersType filters = new FiltersType();
		filters.getInclude().add(".*_EXPORT_.*");
		filters.getInclude().add(".*_EXPORT1024_.*");
		filters.getInclude().add(".*_WITH_DES_.*");
		filters.getInclude().add(".*_WITH_AES_.*");
		filters.getInclude().add(".*_WITH_NULL_.*");
		filters.getExclude().add(".*_DH_anon_.*");
		tlsParams.setCipherSuitesFilter(filters);
		return tlsParams;
	}

	public HTTPClientPolicy httpClientPolicy() {
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(36000);
		httpClientPolicy.setAllowChunking(false);
		httpClientPolicy.setReceiveTimeout(32000);
		return httpClientPolicy;
	}    
    
//	static {
//	    //for localhost testing only
//	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//	    new javax.net.ssl.HostnameVerifier(){
//
//	        public boolean verify(String hostname,
//	                javax.net.ssl.SSLSession sslSession) {
//	            if (hostname.equals("localhost")) {
//	                return true;
//	            }
//	            return false;
//	        }
//	    });
//	}    
//    
//    @Bean
//    public BackendOrderService backendOrderService2() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
//        
//    	QName qName =  new QName("http://soap.backend.order.yuanqingfei.com/","BackendOrderServiceImplService");
//    	BackendOrderServiceImplService bosis = new BackendOrderServiceImplService(new URL("https://localhost:8443/backend-impl/ws/OrderService?wsdl"),qName);
//        BackendOrderService bos = bosis.getBackendOrderServiceImplPort();
//    	
////		URL url = new URL("https://localhost:8443/backend-impl/ws/OrderService?wsdl");
////        QName qname = new QName("http://soap.backend.order.yuanqingfei.com/", "BackendOrderServiceImplService");
////        Service service = Service.create(url, qname);
////        BackendOrderService bos = service.getPort(BackendOrderService.class);
//		return bos;
//    }     
    
    
    @Bean 
    public OrderServiceImpl getOrderService(){
    	return new OrderServiceImpl();
    }
    
    @Bean
    public ConfigProperty configProperty(){
    	ConfigProperty cp = new ConfigProperty();
    	cp.setPassword(password);
    	return cp; 
    }
    @Bean
    public ClientPasswordCallbackHandler getClientPasswordCallbackHandler(){
    	return new ClientPasswordCallbackHandler();
    }
}
