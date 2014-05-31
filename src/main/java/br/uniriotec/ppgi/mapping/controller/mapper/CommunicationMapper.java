package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.communication supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class CommunicationMapper {
	
	private static final String[] agentiveSuffixes = {"ee","er","or","ist","ess"};
	private static final String[] eventiveSuffixes = {"tion","sion","ance","ence"};
	
	
	/**
	 * Maps noun.communication synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 * @throws Exception 
	 */
	public static void mapToSemType(MySynset synset) throws Exception{
		
		//If it is note derivationally related, then it is simply an artifact
		if(MITWordnetUtils.isDerivationallyRelated(synset.getWordnetID()) == false){
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Artefact"));
			
		//If it DOES relationally derives from a verb (is a Deverbal Noun)...
		}else{
			
			//If it is homograph to the verb it derives from then it is an ACTIVITY
			if(MITWordnetUtils.isHomographToVerb(synset.getWordnetID())){
				synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Activity"));

			//check for agentive or passive
			}else{
				if(endsWithSuffix(agentiveSuffixes, synset)){
					synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Artefact"));
				}else if(endsWithSuffix(eventiveSuffixes,synset)){
					synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Activity"));
				}else{
					//Not agent neither event... puts into Other (must be rethinken).
					synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Other"));
				}
			}
		}
	}
	
	
	

	/**
	 * checks if a strings ends with on of the given suffixes
	 * from the inputted list.
	 * 
	 * @param suffixes
	 * @param synset
	 * @return
	 */
	private static boolean endsWithSuffix(String[] suffixes, MySynset synset) {
		for(String word : synset.getWords()){
			for(String suffix : suffixes){
				if(word.endsWith(suffix)){
					return true;
				}
			}
		}
		return false;
	}
	
}
