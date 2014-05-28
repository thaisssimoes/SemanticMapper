package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.uniriotec.ppgi.mapping.model.Supersense;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.ISupersenseDAO;

/**
 * implementation of IMysqlDAO interface to handle
 * Hibernate persistence.
 * 
 * @author felipe
 *
 */
public class MysqlSupersenseDAO implements ISupersenseDAO {

	public void save(Supersense ss) throws SQLException {
		Supersense loadedSS = getByName(ss.getName());
		if(loadedSS == null){
			Session session = HibernateUtil.openSession();
			Transaction tx = session.beginTransaction();
			
			session.saveOrUpdate(ss);
			
			session.flush();
			session.refresh(ss);
			tx.commit();
			session.close();
		}else{
			ss.setId(loadedSS.getId());
		}
		
	}
	
	
	
	public Supersense load(int id) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Supersense ss = (Supersense)session.load(Supersense.class, id);
		
		session.close();
		
		return ss;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public Supersense getByName(String name) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Supersense.class)
				.add( Restrictions.eq("name",name) );
		List results = criteria.list();
		Supersense ss = null;
		if(results.size() > 0){
			ss = (Supersense)results.get(0);
		}
		
		session.close();
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
