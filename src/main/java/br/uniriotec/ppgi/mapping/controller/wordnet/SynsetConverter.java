package br.uniriotec.ppgi.mapping.controller.wordnet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.MySynset;
import br.uniriotec.ppgi.mapping.model.Supersense;
import br.uniriotec.ppgi.mapping.model.dao.DAOFactory;
import br.uniriotec.ppgi.mapping.model.dao.ISupersenseDAO;
import edu.mit.jwi.item.ILexFile;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.LexFile;

/**
 * Class responsible for converting different objects into 
 * MySynset objects. 
 * 
 * @author felipe
 *
 */
public class SynsetConverter {
	
	private static Logger logger = Logger.getLogger(SynsetConverter.class);
	
	/**
	 * Based on a map of lexical file IDs pointing to lists of ISynset objects
	 * create multiple MySynset objects. Each lexical file will become a
	 * Supersense object, that will be added to each MySynset object.
	 * The resulting list does not have Semantic types yet.
	 * 
	 * Eache Supersense object will be persisted to the database (if it does
	 * not exists yes).
	 * 
	 * @param synsetsPerSupersenses
	 * @return
	 * @throws Exception 
	 */
	public static List<MySynset> convertSupersenseMap(Map<Integer, HashSet<ISynset>> synsetsPerSupersenses) throws Exception{
		//Instantiate returned object
		List<MySynset> mySynsetList = new ArrayList<MySynset>();
		
		//Iterate each index of the map
		for(Entry<Integer, HashSet<ISynset>> e : synsetsPerSupersenses.entrySet()){
			//Check for a LexFile object with the given ID
			LexFile.checkLexicalFileNumber(e.getKey());
			ILexFile lexFile = LexFile.getLexicalFile(e.getKey());
			
			//Instantiate a new Supersense Object
			Supersense ss = new Supersense();
				ss.setId(lexFile.getNumber());
				ss.setName(lexFile.getName());
				ss.setPos(lexFile.getPOS());
				ss.setDefinition(lexFile.getDescription());
			
			ISupersenseDAO daoSupersense = DAOFactory.getInstance().getSupersenseDAO();
			try {
				daoSupersense.save(ss);
			} catch (SQLException ex) {
				logger.error(ex);
				throw new Exception(ex);
			}
			
			logger.debug("generating MySynsets for supersense "+ss.getName());
			
			for(ISynset wnSynset : e.getValue()){
				MySynset mySynset = new MySynset();
				mySynset.setWordnetID(wnSynset.getID().toString());
				mySynset.setPos(wnSynset.getPOS());
				mySynset.setGloss(wnSynset.getGloss());
				mySynset.setSupersense(ss);
				mySynset.setWords(convertIWordsToStrings(wnSynset.getWords()));
				
				mySynsetList.add(mySynset);
			}
		}
		
		return mySynsetList;
	}
	
	
	/**
	 * Convert a list of IWord object to a list of strings, where
	 * each string is the lemma of the IWord object.
	 * 
	 * @param iWordList
	 * @return
	 */
	private static List<String> convertIWordsToStrings(List<IWord> iWordList){
		List<String> stringList = new ArrayList<String>();
		for(IWord iWord : iWordList){
			stringList.add(iWord.getLemma());
		}
		return stringList;
	}
	
}
