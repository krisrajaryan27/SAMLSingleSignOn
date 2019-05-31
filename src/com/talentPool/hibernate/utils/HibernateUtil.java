package com.talentPool.hibernate.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.DBUtils;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			Configuration cfg = new Configuration();
			cfg.setProperty("hibernate.connection.driver_class", TPApplicationProperties.getProperty("db.driver"));
			cfg.setProperty("hibernate.connection.url", TPApplicationProperties.getProperty("db.url"));
			cfg.setProperty("hibernate.connection.pool_size", TPApplicationProperties.getProperty("db.pool.max_connections"));
		    cfg.setProperty("hibernate.connection.username", DBUtils.decryptString(TPApplicationProperties.getProperty("db.username")));
		    cfg.setProperty("hibernate.connection.password", DBUtils.decryptString(TPApplicationProperties.getProperty("db.password")));
		    
		    cfg.setProperty("hibernate.c3p0.max_size", TPApplicationProperties.getProperty("db.pool.max_connections"));
		    cfg.setProperty("hibernate.c3p0.min_size", TPApplicationProperties.getProperty("db.pool.initial_connections"));
		    cfg.setProperty("hibernate.c3p0.timeout", "5000");
		    cfg.setProperty("hibernate.c3p0.max_statements", TPApplicationProperties.getProperty("db.pool.max_connections"));		    
		    
			sessionFactory = cfg.configure().buildSessionFactory();
			
		} catch(HibernateException he){
			TPLogger.getLogger().error("Initial SessionFactory creation failed.", he);
			throw new ExceptionInInitializerError(he);
		} catch (Exception ex) {
			TPLogger.getLogger().error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		} 
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
