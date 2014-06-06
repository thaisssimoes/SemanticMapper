package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.ModifierSynsetLoader;
import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.person supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class PersonMapper {
	
	/**
	 * Maps noun.person synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset) throws Exception{
		
		//Traverse Synset's hypernym tree looking for 
		//specific synsets.
		boolean hasRelative = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("Relative").getWordnetID());
		
		boolean hasParent = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("Parent").getWordnetID());
		
		if(hasRelative || hasParent){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Kin"));
		}else{
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Rank"));
		}
		
		
	}
	
}
