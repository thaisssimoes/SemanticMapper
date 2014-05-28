package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;

import br.uniriotec.ppgi.mapping.model.Supersense;

/**
 * Interface for DAO layer to persiste, retrieve,
 * delete and query Supersense objects
 * 
 * @author felipe
 *
 */
public interface ISupersenseDAO {
	
	/**
	 * Saves a Supersense object
	 * @param ss
	 * @throws SQLException
	 */
	void save(Supersense ss) throws SQLException;
	
	/**
	 * Retrieves a Supersense object by its ID
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Supersense load(int id) throws SQLException;
	
	/**
	 * Retrieves a supersens object by its unique name
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	Supersense getByName(String name) throws SQLException;
	
	/**
	 * checks if a supersense is already persisted.
	 * @param ss
	 * @return
	 * @throws SQLException
	 */
	boolean checkIfNameExists(Supersense ss) throws SQLException;
	
}
