package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;
import java.util.List;

import br.uniriotec.ppgi.mapping.model.ModifierSynset;

/**
 * Interface for DAO layer for ModifierSynset objects
 * 
 * @author felipe
 *
 */
public interface IModifierSynsetDAO {
	
	/**
	 * lists all SemanticType objects persisted
	 * @return
	 */
	List<ModifierSynset> listAll() throws SQLException;
	
}
