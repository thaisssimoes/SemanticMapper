package br.uniriotec.ppgi.mapping.model.dao;

import java.sql.SQLException;

import br.uniriotec.ppgi.mapping.model.Supersense;


public interface ISupersenseDAO {

	void save(Supersense ss) throws SQLException;
	public Supersense load(int id) throws SQLException;
	public Supersense getByName(String name) throws SQLException;
	public boolean checkIfNameExists(Supersense ss) throws SQLException;
	
}
