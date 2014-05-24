package br.uniriotec.ppgi.mapping.controller.wordnet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.model.exception.WordnetHelperException;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ILexFile;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class MITWordnetUtils {
	private static Logger logger = Logger.getLogger(MITWordnetUtils.class);
	private static IDictionary dict = null;
	
	
	/**
	 * Retrieves the first possible stem of a word.
	 * 
	 * @param word
	 * @param pos
	 * @return
	 * @throws IOException
	 */
	public static String stemWord(String word, POS pos) throws IOException{
		WordnetStemmer stemmer = new WordnetStemmer(getDictionary());
		return stemmer.findStems(word, pos).get(0);
	}
	
	
	/**
	 * List all synsets under a certain Part-Of-Speech tag (POS), not discriminating by posible
	 * semantic relations it presents. the same as using "listAllSynsets(pos, null, true)"
	 * 
	 * @param pos - the part-of-speech
	 * @return list of all synsets related to the part-of-speech
	 * @throws IOException
	 * @throws WordnetHelperException
	 */
	public static Map<String, List<ISynset>> listAllSynsets(POS pos) throws IOException, WordnetHelperException{
		return listAllSynsets(pos, null, false);
	}
	
	
	/**
	 * List all synsets under a certain Part-Of-Speech tag (POS), filtering by the presence or absence
	 * of a certain Pointer.
	 * 
	 * OBSERVATION: if no Pointer is informed (null) and noPointerRelation is TRUE, no synset will be returned, 
	 * since all synsets have at least on pointer relation.
	 * 
	 * @param pos
	 * @param pointer
	 * @param noPointerRelation
	 * @return
	 * @throws IOException
	 * @throws WordnetHelperException
	 */
	public static Map<String, List<ISynset>> listAllSynsets(POS pos, IPointer pointer, boolean noPointerRelation) throws IOException, WordnetHelperException{
		
		/* *********************************************************************
		 * Checks if the given POS is NULL. if affirmative raises an exception.
		 * *********************************************************************/
		if(pos == null){
			WordnetHelperException e = new WordnetHelperException("A valid POS value must be informed.");
			logger.error(e);
			throw e;
		}
		
		/* *********************************************************************/

		
		//the resulting object
		Map<String, List<ISynset>> synsetsPerSupersenses = new TreeMap<String, List<ISynset>>();
		
		try {
			//Retrieve iterator over all Synsets under a certain Part-Of-Speech tag.
			IDictionary dict = getDictionary();
			Iterator<ISynset> it = dict.getSynsetIterator(pos);
			
			while(it.hasNext()){
				ISynset synset = it.next();
				
				//Determine the number of related synsets considering if a specific
				//pointer was informed or all pointers should be considered
				int relatedSynsetsCount = 0;
				if(pointer == null){
					relatedSynsetsCount = synset.getRelatedSynsets().size();
				}else{
					relatedSynsetsCount = synset.getRelatedSynsets(pointer).size();
				}
				
				/*
				 * Determine if the method should add to the resulting object a synset that 
				 * DOES present a relation of the type specified by the pointer or
				 * if it should only add synsets that DOES NOT present such type of relation.
				 */
				boolean searchAssertion;
				if(noPointerRelation){
					searchAssertion = relatedSynsetsCount == 0; //Will add every synset that DOES NOT present the relation
				}else{
					searchAssertion = relatedSynsetsCount > 0; //Will add every synset that DOES indeed present the relation
				}
				
				//Filter synsets adding them to the resulting object
				if(searchAssertion){
					ILexFile supersense = synset.getLexicalFile();
					
					//Checks if the resulting map already have the supersense as index
					//if negative instantiate a new index position for that supersense
					if(synsetsPerSupersenses.get(supersense.getName()) == null){
						synsetsPerSupersenses.put(supersense.getName(), new ArrayList<ISynset>());
					}
					//adds the synset to its related supersense
					synsetsPerSupersenses.get(supersense.getName()).add(synset);
				}
			}
			
		} catch (IOException e) {
			logger.error(e);
			throw new IOException("Exception when retrieving nouns and their supersenses.",e);
		}
		
		return synsetsPerSupersenses;
		
	}
	

	
	
	
	
	/**
	 * Instantiate a dictionary object used by the MIT JWI api to 
	 * query the Wordnet. The dictionary returned is a singleton.
	 * 
	 * @return the wordnet dictionary
	 * @throws IOException
	 */
	private static synchronized IDictionary getDictionary() throws IOException{
		if(dict == null){
			logger.debug("Instantiating new Wordnet Dictionary");
			// construct the URL to the Wordnet dictionary directory
			String wnhome = System.getenv("WNHOME");
			
			//Verify if the WNHOME environment variable is set and print warn if not
			if(wnhome.trim().equals("")){
				logger.error("The WNHOME variable is not set. Set it to indicate your Wordnet home directory.");
			}
			
			String path = wnhome + File.separator + "dict"; 
			URL url = new URL("file", null, path);
			// build the dictionary object and open it
			dict = new Dictionary(url); 
			dict.open();
		}
		
		return dict;
	}
	
}
