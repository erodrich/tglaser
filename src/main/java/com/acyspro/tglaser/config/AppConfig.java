package com.acyspro.tglaser.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.acyspro.tglaser")
@PropertySource({"classpath:persistence-mysql.properties", "classpath:security-persistence-mysql.properties"})
public class AppConfig implements WebMvcConfigurer{

	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// ViewResolver
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	// Datasource
	@Bean
	public DataSource theDataSource() {
		
		// Create connection pool
		
		ComboPooledDataSource theDataSource = new ComboPooledDataSource();
		
		// Set jdbc driver
		
		try {
			theDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exc) {
			
			throw new RuntimeException(exc);
		}
		
		// for sanity's sake, log username and url to db
		
		logger.info("===>>>jdbc.url= " + env.getProperty("jdbc.url"));
		logger.info("===>>>jdbc.user= " + env.getProperty("jdbc.user"));
		
		//set database connection props
		
		theDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		theDataSource.setUser(env.getProperty("jdbc.user"));
		theDataSource.setPassword(env.getProperty("jdbc.password"));
		
		// set connection pool props
		
		theDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		theDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		theDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		theDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return theDataSource;

	}
	
	private Properties getHibernateProperties() {
		
		// set Hibernate properties
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;
	}
	
	// Security datasource
	@Bean
	public DataSource securityDataSource() {
		
		// create a connection pool
		ComboPooledDataSource theSecurityDataSource = new ComboPooledDataSource();
		
		// set jdbc driver
		try {
			theSecurityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
		} catch (PropertyVetoException exc) {
			
			throw new RuntimeException(exc);
		}
		
		// log connection props for sanity's sake
		logger.info(">>> security.jdbc.url=" + env.getProperty("security.jdbc.url"));
		logger.info(">>> security.jdbc.user=" + env.getProperty("security.jdbc.user"));
		
		// set database connection props
		theSecurityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		theSecurityDataSource.setUser(env.getProperty("security.jdbc.user"));
		theSecurityDataSource.setPassword(env.getProperty("security.jdbc.password"));
		
		// set connection pool props
		theSecurityDataSource.setInitialPoolSize(getIntProperty("security.connection.pool.initialPoolSize"));
		theSecurityDataSource.setMinPoolSize(getIntProperty("security.connection.pool.minPoolSize"));
		theSecurityDataSource.setMaxPoolSize(getIntProperty("security.connection.pool.maxPoolSize"));
		theSecurityDataSource.setMaxIdleTime(getIntProperty("security.connection.pool.maxIdleTime"));
		
		return theSecurityDataSource;
	}
	
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		// create session factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(theDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		// return value
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		
		return txManager;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
	}
	
	
}
