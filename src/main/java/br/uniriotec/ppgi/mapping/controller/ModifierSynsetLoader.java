package br.uniriotec.ppgi.mapping.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.ModifierSynset;
import br.uniriotec.ppgi.mapping.model.dao.DAOFactory;
import br.uniriotec.ppgi.mapping.model.dao.IModifierSynsetDAO;

public class ModifierSynsetLoader {
private static Logger logger = Logger.getLogger(ModifierSynsetLoader.class);
	
	
	private static Map<String, ModifierSynset> modifierMap = new HashMap<String, ModifierSynset>();
	private static ModifierSynsetLoader _instance = null;
	
	public static synchronized ModifierSynsetLoader getInstance() throws Exception{
		if(_instance == null){
			_instance = new ModifierSynsetLoader();
		}
		return _instance;
	}
	
	private ModifierSynsetLoader() throws Exception{
		IModifierSynsetDAO dao = DAOFactory.getInstance().getModifierSynsetDAO();
		try {
			for(ModifierSynset ms : dao.listAll()){
				modifierMap.put(ms.getName(), ms);
			}
		} catch (SQLException e) {
			logger.error("Error when initializing ModifierSynsetLoader.", e);
			throw new Exception(e);
		}
	}
	
	public ModifierSynset getByName(String wnSynsetID){
		return modifierMap.get(wnSynsetID);
	}
}
