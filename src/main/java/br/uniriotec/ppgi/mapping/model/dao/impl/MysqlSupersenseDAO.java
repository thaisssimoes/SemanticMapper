package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.uniriotec.ppgi.mapping.model.Supersense;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.ISupersenseDAO;

public class MysqlSupersenseDAO implements ISupersenseDAO {

	public void save(Supersense ss) throws SQLException {
		Supersense loadedSS = getByName(ss.getName());
		if(loadedSS == null){
			SessionFactory factory =  HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			
			session.saveOrUpdate(ss);
			
			session.flush();
			session.refresh(ss);
			tx.commit();
			session.close();
			factory.close();
		}else{
			ss.setId(loadedSS.getId());
		}
		
	}
	
	
	
	public Supersense load(int id) throws SQLException{
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Supersense ss = (Supersense)session.load(Supersense.class, id);
		
		session.close();
		factory.close();
		
		return ss;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public Supersense getByName(String name) throws SQLException{
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Supersense.class)
				.add( Restrictions.eq("name",name) );
		List results = criteria.list();
		Supersense ss = null;
		if(results.size() > 0){
			ss = (Supersense)results.get(0);
		}
		
		session.close();
		factory.close();
		return ss;
	}
	
	
	public boolean checkIfNameExists(Supersense ss) throws SQLException{
		if(getByName(ss.getName()) == null){
			return false;
		}else{
			return true;
		}
	}


}
