package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import br.uniriotec.ppgi.mapping.model.ModifierSynset;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.IModifierSynsetDAO;

/**
 * implementation of IModifierSynsetDAO interface to handle
 * Hibernate persistence.
 * 
 * @author felipe
 *
 */
public class MysqlModifierSynsetDAO implements IModifierSynsetDAO {
	

	public List<ModifierSynset> listAll() throws SQLException {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<ModifierSynset> list = session.createCriteria(ModifierSynset.class).list();
		
		session.close();
		return list;
		
	}


}
