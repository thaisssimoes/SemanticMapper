package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.artifact supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class ArtifactMapper {
	
	/**
	 * Maps noun.artifact synsets to the corresponding semantic type 
	 * described by Dixon. When the synset is a holonym, besides being
	 * a ARTEFACT the supersense is also a PART.
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset, boolean realMapping) throws Exception{

		//Checks if the synset exposes a Holonym pointer, 
		//if it does then it should be mapped to Parts also
		boolean isHolonym = 
				MITWordnetUtils.isHolonym(synset.getWordnetID());
		
		if(isHolonym){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Parts"),realMapping);
		}
		
		synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Artefact"),realMapping);
		
	}
	
}
