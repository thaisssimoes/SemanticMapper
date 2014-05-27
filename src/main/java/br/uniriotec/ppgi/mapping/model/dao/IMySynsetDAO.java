package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;
import java.util.List;

import br.uniriotec.ppgi.mapping.model.MySynset;

public interface IMySynsetDAO {

	void save(List<MySynset> synsetSamples) throws SQLException;
	MySynset load(int id) throws SQLException;
	MySynset getByWordnetID(String wnID) throws SQLException;
	
}
