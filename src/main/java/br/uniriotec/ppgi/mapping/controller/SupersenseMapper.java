package br.uniriotec.ppgi.mapping.controller;

import java.util.List;

import br.uniriotec.ppgi.mapping.controller.mapper.CommunicationMapper;
import br.uniriotec.ppgi.mapping.controller.mapper.ObjectMapper;
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
					break;
					
				case 4:	//noun.act
					break;
					
				case 5:	//noun.animal
					break;
					
				case 6:	//noun.artifact
					break;
					
				case 7:	//noun.attribute
					break;
					
				case 8:	//noun.body
					break;
					
				case 9:	//	noun.cognition
					break;
					
				case 10: //	noun.communication
					CommunicationMapper.mapToSemType(ms);
					break;
					
				case 11: //	noun.event
					break;
					
				case 12: //	noun.feeling
					break;
					
				case 13: //	noun.food
					break;
					
				case 14: //	noun.group
					break;
					
				case 15: //	noun.location
					break;
					
				case 16: //	noun.motive
					break;
					
				case 17: //	noun.object
					ObjectMapper.mapToSemType(ms);
					break;
					
				case 18: //	noun.person
					break;
					
				case 19: //	noun.phenomenon
					break;
					
				case 20: //	noun.plant
					break;
					
				case 21: //	noun.possession
					break;
					
				case 22: //	noun.process
					break;
					
				case 23: //	noun.quantity
					break;
					
				case 24: //	noun.relation
					break;
					
				case 25: //	noun.shape
					break;
					
				case 26: //	noun.state
					break;
					
				case 27: //	noun.substance
					break;
					
				case 28: //	noun.time
					break;
					
			}
			
		}
		
	}
}
