package br.uniriotec.ppgi.mapping.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import br.uniriotec.ppgi.mapping.model.Parameter;
import br.uniriotec.ppgi.mapping.model.dao.HibernateUtil;
import br.uniriotec.ppgi.mapping.model.dao.IParameterDAO;

/**
 * implementation of IParameterDAO interface to handle
 * Hibernate persistence.
 * 
 * @author felipe
 *
 */
public class MysqlParameterDAO implements IParameterDAO {
	

	public List<Parameter> listAll() throws SQLException {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Parameter> list = session.createCriteria(Parameter.class).list();
		
		session.close();
		return list;
		
	}


}
