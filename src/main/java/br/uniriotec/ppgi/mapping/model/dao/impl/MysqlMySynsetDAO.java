package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.uniriotec.ppgi.mapping.model.MySynset;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.IMySynsetDAO;

public class MysqlMySynsetDAO implements IMySynsetDAO {

	public void save(List<MySynset> synsetSamples) throws SQLException {
		SessionFactory factory =  HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		for(MySynset synset : synsetSamples){
			session.save(synset);
		}
		session.flush();
		tx.commit();
		session.close();
		factory.close();
		
	}

}
