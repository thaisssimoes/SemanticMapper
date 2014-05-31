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
	public static void mapToSemType(MySynset synset) throws Exception{
		
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
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Animate"));
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Parts"));
		}else if(hasGreenGoods){
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Flora"));
		}else if(hasFoodProduct){
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Parts"));
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Artefact"));
		}else{
			synset.getSemanticType().add(SemanticTypeLoader.getInstance().getByName("Artefact"));
		}
		
		
	}
	
}
