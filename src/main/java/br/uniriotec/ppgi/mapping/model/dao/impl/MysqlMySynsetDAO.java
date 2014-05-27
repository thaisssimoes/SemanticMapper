package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.uniriotec.ppgi.mapping.model.MySynset;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.IMySynsetDAO;

public class MysqlMySynsetDAO implements IMySynsetDAO {

	public void save(List<MySynset> synsetSamples) throws SQLException {
		Session session = HibernateUtil.openSession();
		Transaction tx = session.beginTransaction();
		
		for(MySynset synset : synsetSamples){
			session.save(synset);
		}
		
		session.flush();
		tx.commit();
		session.close();
		
	}
	
	
	public MySynset load(int id) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		MySynset ms = (MySynset)session.load(MySynset.class, id);
		
		session.close();
		
		return ms;
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public MySynset getByWordnetID(String wnID) throws SQLException{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(MySynset.class)
				.add( Restrictions.eq("wordnetID",wnID) );
		List results = criteria.list();
		MySynset ms = null;
		if(results.size() > 0){
			ms = (MySynset)results.get(0);
		}
		
		session.close();
		return ms;
	}
	

}
