package br.uniriotec.ppgi.mapping.model.dao;

import br.uniriotec.ppgi.mapping.model.dao.impl.MysqlModifierSynsetDAO;
import br.uniriotec.ppgi.mapping.model.dao.impl.MysqlMySynsetDAO;
import br.uniriotec.ppgi.mapping.model.dao.impl.MysqlParameterDAO;
import br.uniriotec.ppgi.mapping.model.dao.impl.MysqlSemanticTypeDAO;
import br.uniriotec.ppgi.mapping.model.dao.impl.MysqlSupersenseDAO;

/**
 * A singleton resonsible for retrieving the right object
 * for a certain DAO interface.
 * @author felipe
 *
 */
public class DAOFactory {
	private static DAOFactory _instance = null;
	
	private DAOFactory(){}
	
	public static synchronized DAOFactory getInstance(){
		if(_instance == null){
			_instance = new DAOFactory();
		}
		return _instance;
	}
	
	public IMySynsetDAO getMySynsetDAO(){
		return new MysqlMySynsetDAO();
	}
	
	public ISupersenseDAO getSupersenseDAO(){
		return new MysqlSupersenseDAO();
	}
	
	public ISemanticTypeDAO getSemanticTypeDAO(){
		return new MysqlSemanticTypeDAO();
	}
	
	public IModifierSynsetDAO getModifierSynsetDAO(){
		return new MysqlModifierSynsetDAO();
	}
	
	public IParameterDAO getParameterDAO(){
		return new MysqlParameterDAO();
	}
}
