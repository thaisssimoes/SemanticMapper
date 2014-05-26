package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.uniriotec.ppgi.mapping.model.MySynset;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.IMySynsetDAO;

public class MysqlMySynsetDAO implements IMySynsetDAO {

	public void save(List<MySynset> synsetSamples) throws SQLException {
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		for(MySynset synset : synsetSamples){
			MySynset loadedMS = getByWordnetID(synset.getWordnetID());
			if(loadedMS == null){
				Transaction tx = session.beginTransaction();
				session.save(synset);
				session.flush();
				tx.commit();
			}else{
				synset.setWordnetID(loadedMS.getWordnetID());
			}
		}
		
		session.close();
		factory.close();
		
	}
	
	
	public MySynset load(int id) throws SQLException{
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		MySynset ms = (MySynset)session.load(MySynset.class, id);
		
		session.close();
		factory.close();
		
		return ms;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public MySynset getByWordnetID(String wnID) throws SQLException{
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(MySynset.class)
				.add( Restrictions.eq("wordnetID",wnID) );
		List results = criteria.list();
		MySynset ms = null;
		if(results.size() > 0){
			ms = (MySynset)results.get(0);
		}
		
		session.close();
		factory.close();
		return ms;
	}
	

}
