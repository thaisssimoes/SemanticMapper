package br.uniriotec.ppgi.mapping.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Instantiates a Hibernate Factory to deal with the 
 * persistence layer. The factory create sessions that
 * can be used to persist, query, retrieve and delete 
 * other objects.
 * 
 * @author felipe
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	
	/**
	 * instantiates the factory as a singleton. Meaning that
	 * a factory is only created if SessionFactory object has not yet 
	 * been initialized or if for some reason it has been closed.
	 * @return
	 */
	private static synchronized SessionFactory createSessionFactory() {
		if(sessionFactory == null){
		    Configuration configuration = new Configuration();
		    configuration.configure();
		    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
	    return sessionFactory;
	}
	
	/**
	 * Retrieves a SessionFactory configured by Hibernate.
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
        return createSessionFactory();

    }
	
	/**
	 * Closes the current instantiated session factory.
	 */
	public static void closeSessionFactory(){
		if(sessionFactory != null){
			sessionFactory.close();
		}
	}
	
	/**
	 * Opens a Session factory object. Once the session
	 * has been used the user will have to close it manually
	 * by calling session.close();
	 * @return
	 */
	public static Session openSession(){
		return createSessionFactory().openSession();
	}
}
