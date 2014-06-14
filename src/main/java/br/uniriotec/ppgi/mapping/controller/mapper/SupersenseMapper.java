package br.uniriotec.ppgi.mapping.controller.mapper;

import java.util.List;
import java.util.Random;

import br.uniriotec.ppgi.mapping.controller.SemanticTypeLoader;
import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Class responsible for mapping Wordnet synsest associated
 * to the their respective Supersenses to other classes (e.g. Semantic Types)
 * 
 * @author felipe
 *
 */
public class SupersenseMapper {
	
	
	private static final int[] supersenseIds = 
		{4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
	
	
	/**
	 * Determine the Semantic Type of each MySynset object in the inputted list
	 * based on their Supersenses, adjusting them by reference. generates positive
	 * examples only.
	 * 
	 * @param synsets - list of synsets to be mapped to semantic types
	 * @throws Exception
	 */
	public static void mapSupersensesToSemanticTypes(List<MySynset> synsets) 
			throws Exception{
		
		mapSupersensesToSemanticTypes(synsets, true);
		
	}
	
	
	
	
	/**
	 * Determine the Semantic Type of each MySynset object in the inputted list
	 * based on their Supersenses, adjusting them by reference. If the parameter
	 * positiveExample is FALSE, then the mappings are forcelly mistaken, generating
	 * false examples.
	 * 
	 * @param synsets - list of synsets to be mapped to semantic types
	 * @param positiveExamples - if the examples should be mapped to positive 
	 * 			or negative examples
	 * @throws Exception 
	 */
	public static void mapSupersensesToSemanticTypes(
			List<MySynset> synsets, boolean positiveExamples) 
			throws Exception{
		
		
		for(MySynset ms : synsets){
			
			//If the caller wants negative examples, use the mapping rule for
			//anoter supersense instead.
			if(positiveExamples == false){
				while(true){
					int altSupersense = chooseRandomSupersense();
					if(altSupersense != ms.getSupersense().getId()){
						ms.getSupersense().setId(altSupersense);
						break;
					}
				}
			}
			
			switch(ms.getSupersense().getId()){
				case 3:	//noun.Tops	
					//Ignore, since tops should not even exist as an actual Supersense
					break;
					
				case 4:	//noun.act
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Activity"),positiveExamples);
					break;
					
				case 5:	//noun.animal
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Animate"),positiveExamples);
					break;
					
				case 6:	//noun.artifact
					ArtifactMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 7:	//noun.attribute
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("States & Properties"),positiveExamples);
					break;
					
				case 8:	//noun.body
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Parts"),positiveExamples);
					break;
					
				case 9:	//	noun.cognition
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Other"),positiveExamples);
					break;
					
				case 10: //	noun.communication
					CommunicationMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 11: //	noun.event
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Activity"),positiveExamples);
					break;
					
				case 12: //	noun.feeling
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Other"),positiveExamples);
					break;
					
				case 13: //	noun.food
					FoodMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 14: //	noun.group
					try{
						GroupMapper.mapToSemType(ms,positiveExamples);
					}catch(Exception e){
						e.printStackTrace();System.exit(1);
					}
					break;
					
				case 15: //	noun.location
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Place"),positiveExamples);
					break;
					
				case 16: //	noun.motive
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Other"),positiveExamples);
					break;
					
				case 17: //	noun.object
					ObjectMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 18: //	noun.person
					PersonMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 19: //	noun.phenomenon
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Celestial & Weather"),positiveExamples);
					break;
					
				case 20: //	noun.plant
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Flora"),positiveExamples);
					break;
					
				case 21: //	noun.possession
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Other"),positiveExamples);
					break;
					
				case 22: //	noun.process
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Activity"),positiveExamples);
					break;
					
				case 23: //	noun.quantity
					QuantityMapper.mapToSemType(ms,positiveExamples);
					break;
					
				case 24: //	noun.relation
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Other"),positiveExamples);
					break;
					
				case 25: //	noun.shape
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Variety"),positiveExamples);
					break;
					
				case 26: //	noun.state
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("States & Properties"),positiveExamples);
					break;
					
				case 27: //	noun.substance
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Environment"),positiveExamples);
					break;
					
				case 28: //	noun.time
					ms.addSemanticType(
							SemanticTypeLoader.getInstance().getByName("Time"),positiveExamples);
					break;
					
			}
			
		}
		
	}



	/**
	 * selects a random supersense id within the range of
	 * supersenses with mapping rules stablished.
	 * 
	 * @return
	 */
	private static int chooseRandomSupersense() {
		int rnd = new Random().nextInt(supersenseIds.length);
	    return supersenseIds[rnd];
	}
}
