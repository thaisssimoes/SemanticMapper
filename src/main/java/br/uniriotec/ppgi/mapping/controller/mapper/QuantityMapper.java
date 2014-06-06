package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.ModifierSynsetLoader;
import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.quantity supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class QuantityMapper {
	
	/**
	 * Maps noun.quantity synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset) throws Exception{
		
		//Traverse Synset's hypernym tree looking for 
		//specific synsets.
		boolean hasTimePeriod = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("TimePeriod").getWordnetID());
		
		if(hasTimePeriod){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Time"));
		}else{
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Quantity"));
		}
		
		
	}
	
}
