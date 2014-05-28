package br.uniriotec.ppgi.mapping.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.SemanticType;
import br.uniriotec.ppgi.mapping.model.dao.DAOFactory;
import br.uniriotec.ppgi.mapping.model.dao.ISemanticTypeDAO;

public class SemanticTypeLoader {
	
	private static Logger logger = Logger.getLogger(SemanticTypeLoader.class);
	
	
	private static Map<String, SemanticType> semanticTypeMap = new HashMap<String, SemanticType>();
	private static SemanticTypeLoader _instance = null;
	
	public static synchronized SemanticTypeLoader getInstance() throws Exception{
		if(_instance == null){
			_instance = new SemanticTypeLoader();
		}
		return _instance;
	}
	
	private SemanticTypeLoader() throws Exception{
		ISemanticTypeDAO dao = DAOFactory.getInstance().getSemanticTypeDAO();
		try {
			for(SemanticType st : dao.listAll()){
				semanticTypeMap.put(st.getName(), st);
			}
		} catch (SQLException e) {
			logger.error("Error when initializing SemanticTypeLoader.", e);
			throw new Exception(e);
		}
	}
	
	public SemanticType getByName(String semanticTypeName){
		return semanticTypeMap.get(semanticTypeName);
	}
}
