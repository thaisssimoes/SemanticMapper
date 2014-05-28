package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;
import java.util.List;

import br.uniriotec.ppgi.mapping.model.MySynset;

public interface IMySynsetDAO {
	
	/**
	 * Persists a list of MySynset objects
	 * @param synsetSamples
	 * @throws SQLException
	 */
	void save(List<MySynset> synsetSamples) throws SQLException;
	
	/**
	 * Retrieves a MySynset object by its ID, which is the ID
	 * of the corresponding Synset on Wordnet.
	 * @param wnID
	 * @return
	 * @throws SQLException
	 */
	MySynset getByWordnetID(String wnID) throws SQLException;
	
}
