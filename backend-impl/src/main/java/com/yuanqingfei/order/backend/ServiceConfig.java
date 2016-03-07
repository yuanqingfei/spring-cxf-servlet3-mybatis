package com.yuanqingfei.order.backend;

import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.yuanqingfei.order.backend.mq.MessageSender;
import com.yuanqingfei.order.backend.mq.OrderCollector;

@Configuration
@PropertySource({ "classpath:prop/jms.properties", "classpath:prop/mybatis.properties" })
@ComponentScan(basePackages = { "com.yuanqingfei.order.backend" }, excludeFilters = {
		@Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebInitializer.class),
		@Filter(type = FilterType.ASSIGNABLE_TYPE, value = CxfConfig.class) })
@MapperScan({ "com.yuanqingfei.order.backend.mybatis" })
public class ServiceConfig extends AnnotationConfigWebApplicationContext {

	@Value("${jms.broker.url:127.0.0.1}")
	public String brokerUrl;

	@Value("${jms.queue.name:orderQueue}") //	@Value("#{jms['jms.queue.name']?:'orderQueue'}")
	public String jmsQueueName;
	
	@Value("${jdbc.url}") 
	public String jdbcUrl;
	
	@Value("${jdbc.username}") 
	public String jdbcUserName;
	
	@Value("${jdbc.password}") 
	public String jdbcPassword;
	
	@Value("${jdbc.validationQuery}") 
	public String jdbcValidationQuery;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ActiveMQConnectionFactory amqConFactory() {
		ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory(brokerUrl);
		fac.setUseAsyncSend(true);
		return fac;
	}

	@Bean
	public CachingConnectionFactory conFactory() {
		CachingConnectionFactory fac = new CachingConnectionFactory(amqConFactory());
		return fac;
	}

	@Bean
	public ActiveMQQueue amqQueue() {
		ActiveMQQueue queue = new ActiveMQQueue(jmsQueueName);
		return queue;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(conFactory());
		template.setDefaultDestination(amqQueue());
		return template;
	}

	@Bean
	public MessageSender sender() {
		MessageSender sender = new MessageSender(jmsTemplate());
		return sender;
	}

	@Bean
	public OrderCollector receiver() {
		OrderCollector receiver = new OrderCollector();
		return receiver;
	}

	@Bean
	public SimpleMessageListenerContainer receiverContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(conFactory());
		container.setDestinationName(jmsQueueName);
		container.setMessageListener(receiver());
		return container;
	}
	
	@Bean
	public DataSource druidDataSource(){
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(jdbcUrl);
		ds.setPassword(jdbcPassword);
		ds.setUsername(jdbcUserName);
		ds.setValidationQuery(jdbcValidationQuery);
		//TDOD : add other properties
		return ds;
	}
	
	@Bean
	public SqlSessionFactoryBean sessionFactoryBean(){
		SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
		sfb.setDataSource(druidDataSource());
		return sfb;
	}

}
