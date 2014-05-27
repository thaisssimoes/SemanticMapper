package br.uniriotec.ppgi.mapping.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private static SessionFactory createSessionFactory() {
		if(sessionFactory == null){
		    Configuration configuration = new Configuration();
		    configuration.configure();
		    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
	    return sessionFactory;
	}
	
	public static SessionFactory getSessionFactory() {
        return createSessionFactory();

    }
	
	public static void closeSessionFactory(){
		if(sessionFactory != null){
			sessionFactory.close();
		}
	}
	
	public static Session openSession(){
		return createSessionFactory().openSession();
	}
}
