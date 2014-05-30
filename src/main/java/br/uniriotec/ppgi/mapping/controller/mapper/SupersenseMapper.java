package br.uniriotec.ppgi.mapping.controller.mapper;

import java.util.List;

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
	
	/**
	 * Determine the Semantic Type of each MySynset object in the inputted list
	 * based on their Supersenses, adjusting them by reference. 
	 * 
	 * @param synsets
	 * @throws Exception 
	 */
	public static void mapSupersensestoSemanticTypes(List<MySynset> synsets) throws Exception{
		
		for(MySynset ms : synsets){
			
			switch(ms.getSupersense().getId()){
				case 3:	//noun.Tops	
					//Ignore, since tops should not even exist as an actual Supersense
					break;
					
				case 4:	//noun.act
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Activity"));
					break;
					
				case 5:	//noun.animal
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Animate"));
					break;
					
				case 6:	//noun.artifact
					ArtifactMapper.mapToSemType(ms);
					break;
					
				case 7:	//noun.attribute
					break;
					
				case 8:	//noun.body
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Parts"));
					break;
					
				case 9:	//	noun.cognition
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Other"));
					break;
					
				case 10: //	noun.communication
//					CommunicationMapper.mapToSemType(ms);
					break;
					
				case 11: //	noun.event
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Activity"));
					break;
					
				case 12: //	noun.feeling
					break;
					
				case 13: //	noun.food
					break;
					
				case 14: //	noun.group
					break;
					
				case 15: //	noun.location
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Place"));
					break;
					
				case 16: //	noun.motive
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Other"));
					break;
					
				case 17: //	noun.object
					ObjectMapper.mapToSemType(ms);
					break;
					
				case 18: //	noun.person
					break;
					
				case 19: //	noun.phenomenon
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Celestial & Weather"));
					break;
					
				case 20: //	noun.plant
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Flora"));
					break;
					
				case 21: //	noun.possession
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Other"));
					break;
					
				case 22: //	noun.process
					break;
					
				case 23: //	noun.quantity
					break;
					
				case 24: //	noun.relation
					break;
					
				case 25: //	noun.shape
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Variety"));
					break;
					
				case 26: //	noun.state
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("States"));
					break;
					
				case 27: //	noun.substance
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Environment"));
					break;
					
				case 28: //	noun.time
					ms.getSemanticType().add(
							SemanticTypeLoader.getInstance().getByName("Time"));
					break;
					
			}
			
		}
		
	}
}
