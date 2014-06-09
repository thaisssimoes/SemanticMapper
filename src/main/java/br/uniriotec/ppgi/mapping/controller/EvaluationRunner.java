package br.uniriotec.ppgi.mapping.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.mapper.SupersenseMapper;
import br.uniriotec.ppgi.mapping.controller.sampler.SampleSelector;
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
public class EvaluationRunner {
	private static Logger logger = Logger.getLogger(EvaluationRunner.class);
	
	/**
	 * Runs the application. It reads all synsets, select samples for 
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
			 * 		Select Samples for positive and negative 		**
			 * 	examples											**
			 * *******************************************************/
			logger.info("-- Calculating Samples sizes.");
			Map<Integer, Integer> positiveSamplesSizes = 
					SampleSelector.calculateSupersenseSamplesSizes(synsetsPerSupersenses);
			
			Map<Integer, Integer> negativeSamplesSizes = 
					SampleSelector.calculateNegativeSamplesSizes(positiveSamplesSizes);
			

			
			//instatiate a new map of "synset per supersense" to be used as negative samples
			Map<Integer, HashSet<ISynset>> negativeSamples = new HashMap<Integer, HashSet<ISynset>>();
			negativeSamples.putAll(synsetsPerSupersenses);
			
			logger.info("-- Randomically choosing sample synsets.");
			SampleSelector.selectSampleSynsets(synsetsPerSupersenses, positiveSamplesSizes);
			SampleSelector.selectSampleSynsets(negativeSamples, negativeSamplesSizes);
			
			
			
			
			//Convert samples to a lists of MySynset objects
			List<MySynset> positiveSynsetSamples = 
					SynsetConverter.convertSupersenseMap(synsetsPerSupersenses);
			
			List<MySynset> negativeSynsetSamples = 
					SynsetConverter.convertSupersenseMap(negativeSamples);
			
			
			
			
			
			
			
			
			
			
			/* *******************************************************
			 * 		Map synsets to Semantic Types, adjusting 		**
			 * 	them by reference									**
			 * *******************************************************/
			logger.info("-- Mapping Synsets to Semantic Types.");
			
			//Map synsets to Semantic Types correctly
			SupersenseMapper.mapSupersensesToSemanticTypes(positiveSynsetSamples);
			
			//Map synsets to Semantic Types wrongly (creating false examples)
			SupersenseMapper.mapSupersensesToSemanticTypes(negativeSynsetSamples, false);
			
			
			
			
			
			
			
			/* *******************************************************
			 * 		Persist relations between sampled synsets, 		**
			 * 	supersenses and semantic types on Database 			**
			 * *******************************************************/
			logger.info("-- Persisting mappings on database.");
			
			try{
				IMySynsetDAO dao = DAOFactory.getInstance().getMySynsetDAO();
				dao.save(positiveSynsetSamples);
				dao.save(negativeSynsetSamples);
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
