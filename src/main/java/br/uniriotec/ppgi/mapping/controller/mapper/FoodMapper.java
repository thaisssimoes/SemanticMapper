package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.controller.ModifierSynsetLoader;
import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.food supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class FoodMapper {
	
	/**
	 * Maps noun.food synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 * @throws Exception 
	 */	
	public static void mapToSemType(MySynset synset, boolean realMapping) throws Exception{
		
		//Traverse Synset's hypernym tree looking for 
		//specific synsets.
		boolean hasMeat = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("Meat").getWordnetID());
		
		boolean hasGreenGoods = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("GreenGoods").getWordnetID());
		
		boolean hasFoodProduct = 
				MITWordnetUtils.hasModifierSynset(synset.getWordnetID(), 
				ModifierSynsetLoader.getInstance().getByName("FoodProduct").getWordnetID());
		
		if(hasMeat){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Animate"),realMapping);
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Parts"),realMapping);
		}else if(hasGreenGoods){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Flora"),realMapping);
		}else if(hasFoodProduct){
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Parts"),realMapping);
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Artefact"),realMapping);
		}else{
			synset.addSemanticType(SemanticTypeLoader.getInstance().getByName("Artefact"),realMapping);
		}
		
		
	}
	
}
