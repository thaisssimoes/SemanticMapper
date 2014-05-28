package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;
import java.util.List;

import br.uniriotec.ppgi.mapping.model.SemanticType;

/**
 * Interface for DAO layer to persiste, retrieve,
 * delete and query SemanticType objects
 * 
 * @author felipe
 *
 */
public interface ISemanticTypeDAO {
	
	/**
	 * Saves a  object
	 * @param ss
	 * @throws SQLException
	 */
	void save(SemanticType st) throws SQLException;
	
	/**
	 * Retrieves a SemanticType object by its ID
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	SemanticType load(int id) throws SQLException;
	
	/**
	 * Retrieves a SemanticType object by its unique name
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	SemanticType getByName(String name) throws SQLException;
	
	/**
	 * checks if a SemanticType is already persisted.
	 * @param ss
	 * @return
	 * @throws SQLException
	 */
	boolean checkIfNameExists(SemanticType st) throws SQLException;

	/**
	 * lists all SemanticType objects persisted
	 * @return
	 */
	List<SemanticType> listAll() throws SQLException;
	
}
