package br.uniriotec.ppgi.mapping.controller.wordnet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ILexFile;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
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
	
	
	public static void listAllNouns(){
		try {
			IDictionary dict = getDictionary();
			Iterator<ISynset> it = dict.getSynsetIterator(POS.NOUN);
			
			Map<String, List<ISynset>> synsetsPerSupersenses = new TreeMap<String, List<ISynset>>();
			
			while(it.hasNext()){
				ISynset synset = it.next();
				if(synset.getRelatedSynsets(Pointer.HYPONYM).size() == 0){
					ILexFile supersense = synset.getLexicalFile();
					if(synsetsPerSupersenses.get(supersense.getName()) == null){
						synsetsPerSupersenses.put(supersense.getName(), new ArrayList<ISynset>());
					}
					synsetsPerSupersenses.get(supersense.getName()).add(synset);
				}
			}
			
			for(Entry<String, List<ISynset>> e : synsetsPerSupersenses.entrySet()){
				System.out.println("Supersense: "+e.getKey());
				System.out.println(" -- Total de synsets: "+e.getValue().size());
				System.out.println(" -- "+e.getValue().get(0));
				System.out.println(" -- "+e.getValue().get(0).getGloss());
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void returnAllRelations(IIndexWord idxWord){
//		idxWord.
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
			
			//Verify if the WNHOME environment variable is set and print warn inf not
			if(wnhome.trim().equals("")){
				logger.error("The WNHOME variable is not set. Set it to indicate your Wordnet home directory.");
			}
			
			String path = wnhome + File.separator + "dict"; 
			URL url = new URL("file", null, path);
			// construct the dictionary object and open it
			dict = new Dictionary(url); 
			dict.open();
		}
		
		return dict;
	}
	
}
