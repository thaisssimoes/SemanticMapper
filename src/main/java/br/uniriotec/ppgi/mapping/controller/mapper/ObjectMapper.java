package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.ModifierSynsetLoader;
import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.object supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class ObjectMapper {
	
	/**
	 * Maps noun.object synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset, boolean realMapping) throws Exception{
		
		//Traverse Synset's hypernym tree looking for 
		//specific synsets.
		boolean hasExtraterrestrialBody = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("ExtraterrestrialObject").getWordnetID());
		
		boolean hasCelestialObject = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("CelestialBody").getWordnetID());
		
		if(hasExtraterrestrialBody || hasCelestialObject){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Celestial & Weather"),realMapping);
		}else{
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Environment"),realMapping);
		}
		
		
	}
	
}
