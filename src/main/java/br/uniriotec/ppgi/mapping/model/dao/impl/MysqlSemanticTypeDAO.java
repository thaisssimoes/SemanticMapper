package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.uniriotec.ppgi.mapping.model.SemanticType;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.ISemanticTypeDAO;

/**
 * implementation of ISemanticTypeDAO interface to handle
 * Hibernate persistence.
 * 
 * @author felipe
 *
 */
public class MysqlSemanticTypeDAO implements ISemanticTypeDAO {
	
	public SemanticType load(int id) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		SemanticType st = (SemanticType)session.load(SemanticType.class, id);
		
		session.close();
		
		return st;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public SemanticType getByName(String name) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(SemanticType.class)
				.add( Restrictions.eq("name",name) );
		List results = criteria.list();
		SemanticType st = null;
		if(results.size() > 0){
			st = (SemanticType)results.get(0);
		}
		
		session.close();
		return st;
	}
	
	
	
	
	public void save(SemanticType st) throws SQLException {
		SemanticType loadedST = getByName(st.getName());
		if(loadedST == null){
			Session session = HibernateUtil.openSession();
			Transaction tx = session.beginTransaction();
			
			session.saveOrUpdate(st);
			
			session.flush();
			session.refresh(st);
			tx.commit();
			session.close();
		}else{
			st.setId(loadedST.getId());
		}
	}


	
	

	public boolean checkIfNameExists(SemanticType st) throws SQLException {
		if(getByName(st.getName()) == null){
			return false;
		}else{
			return true;
		}
	}




	public List<SemanticType> listAll() throws SQLException {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<SemanticType> list = session.createCriteria(SemanticType.class).list();
		
		session.close();
		return list;
		
	}


}
