package br.uniriotec.ppgi.mapping.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.mapper.SupersenseMapper;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.controller.wordnet.SynsetConverter;
import br.uniriotec.ppgi.mapping.model.MySynset;
import br.uniriotec.ppgi.mapping.model.dao.DAOFactory;
import br.uniriotec.ppgi.mapping.model.dao.IMySynsetDAO;
import br.uniriotec.ppgi.mapping.model.exception.WordnetHelperException;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.LexFile;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

/**
 * Class responsible for orchestrating the application steps.
 * 
 * @author felipe
 *
 */
public class DefaultRunner {
	private static Logger logger = Logger.getLogger(DefaultRunner.class);
	
	/**
	 * Runs the application in default mode. It reads all synsets, select samples for 
	 * each supersense, conducts necessary convertions and mappings to 
	 * Semantic Types, persinsting the results in the Database.
	 */
	public void run(){
		
		try{
			
			/* *******************************************************
			 * 		Retrieve all synsets for each supersense		**
			 * *******************************************************/
			logger.info("-- Retrieving all synsets for each supersense.");
			
			//List only nouns that DOES NOT present a hyponym relation to other nouns
			Map<Integer, HashSet<ISynset>> synsetsPerSupersenses = 
					MITWordnetUtils.listAllSynsets(POS.NOUN, Pointer.HYPONYM, true);
			
			//debug log
			for(Entry<Integer, HashSet<ISynset>> e : synsetsPerSupersenses.entrySet()){
				logger.debug("Supersense: "+LexFile.getLexicalFile(e.getKey())+
						"| -- Total de synsets: "+e.getValue().size());
			}
			
			
			
			
			
			
			/* *******************************************************
			 * 		Instead of selecting samples simply uses all 	**
			 * 	synsets												**
			 * *******************************************************/
			
			
			//Convert synsets to a lists of MySynset objects
			List<MySynset> positiveSynsetSamples = 
					SynsetConverter.convertSupersenseMap(synsetsPerSupersenses);
		
			
			
			
			
			
			
			
			
			/* *******************************************************
			 * 		Map synsets to Semantic Types, adjusting 		**
			 * 	them by reference									**
			 * *******************************************************/
			logger.info("-- Mapping Synsets to Semantic Types.");
			
			//Map synsets to Semantic Types correctly
			SupersenseMapper.mapSupersensesToSemanticTypes(positiveSynsetSamples);
			
			
			
			
			
			
			
			/* *******************************************************
			 * 		Persist relations between sampled synsets, 		**
			 * 	supersenses and semantic types on Database 			**
			 * *******************************************************/
			logger.info("-- Persisting mappings on database.");
			
			try{
				IMySynsetDAO dao = DAOFactory.getInstance().getMySynsetDAO();
				dao.save(positiveSynsetSamples);
			}catch(SQLException e){
				//If exception thrown, abort application
				logger.error("Error while persisting resulting mappings.",e);
				throw e;
			}
			
			
			
			
		}catch(WordnetHelperException e){
			logger.error(e);
			e.printStackTrace();	//Remove stacktrace
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();	//Remove stacktrace
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();	//Remove stacktrace
		}
		
		
	}
	
}
