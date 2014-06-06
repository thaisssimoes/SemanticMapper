package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.ModifierSynsetLoader;
import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.group supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class GroupMapper {
	
	/**
	 * Maps noun.group synsets to the corresponding semantic type 
	 * described by Dixon. 
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset) throws Exception{

		//Traverse Synset's hypernym tree looking for 
		//specific synsets.
		boolean hasFlora = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("PlantFlora").getWordnetID());
		
		boolean hasNaturalObject = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("NaturalObject").getWordnetID());
		
		boolean hasSubstance = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("Substance").getWordnetID());
		
		boolean hasSocialGroup = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("SocialGroup").getWordnetID());
		
		if(hasSocialGroup){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Social Group"));
		}else{
			if(hasFlora || hasNaturalObject || hasSubstance){
				synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Object"));
			}else{
				synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Artefact"));
			}
		}
		
	}
	
}
