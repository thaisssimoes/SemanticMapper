package br.uniriotec.ppgi.mapping.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.Parameter;
import br.uniriotec.ppgi.mapping.model.dao.DAOFactory;
import br.uniriotec.ppgi.mapping.model.dao.IParameterDAO;

public class ParameterLoader {
private static Logger logger = Logger.getLogger(ParameterLoader.class);
	
	
	private static Map<String, Double> parameters = new HashMap<String, Double>();
	private static ParameterLoader _instance = null;
	
	public static synchronized ParameterLoader getInstance() throws Exception{
		if(_instance == null){
			_instance = new ParameterLoader();
		}
		return _instance;
	}
	
	private ParameterLoader() throws Exception{
		IParameterDAO dao = DAOFactory.getInstance().getParameterDAO();
		try {
			for(Parameter p : dao.listAll()){
				parameters.put(p.getName(), p.getValue());
			}
		} catch (SQLException e) {
			logger.error("Error when initializing ModifierSynsetLoader.", e);
			throw new Exception(e);
		}
	}
	
	public Double getByName(String paramName){
		return parameters.get(paramName);
	}
}
