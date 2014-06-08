package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;
import java.util.List;

import br.uniriotec.ppgi.mapping.model.Parameter;

/**
 * Interface for DAO layer for Parameter objects
 * 
 * @author felipe
 *
 */
public interface IParameterDAO {
	
	/**
	 * lists all SemanticType objects persisted
	 * @return
	 */
	List<Parameter> listAll() throws SQLException;
	
}
