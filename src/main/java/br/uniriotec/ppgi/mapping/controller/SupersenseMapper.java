package br.uniriotec.ppgi.mapping.controller;

import java.util.List;

import br.uniriotec.ppgi.mapping.controller.mapper.CommunicationMapper;
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
	 */
	public static void mapSupersensestoSemanticTypes(List<MySynset> synsets){
		
		for(MySynset ms : synsets){
			
			switch(ms.getSupersense().getId()){
				case 3:	//noun.Tops	
					
				case 4:	//noun.act
				
				case 5:	//noun.animal
					
				case 6:	//noun.artifact
					
				case 7:	//noun.attribute
					
				case 8:	//noun.body
					
				case 9:	//	noun.cognition
					
				case 10: //	noun.communication
					CommunicationMapper.mapToSemType(ms);
					break;
					
				case 11: //	noun.event
					
				case 12: //	noun.feeling
					
				case 13: //	noun.food
					
				case 14: //	noun.group
					
				case 15: //	noun.location
					
				case 16: //	noun.motive
					
				case 17: //	noun.object
					
				case 18: //	noun.person
					
				case 19: //	noun.phenomenon
					
				case 20: //	noun.plant
					
				case 21: //	noun.possession
					
				case 22: //	noun.process
					
				case 23: //	noun.quantity
					
				case 24: //	noun.relation
					
				case 25: //	noun.shape
					
				case 26: //	noun.state
					
				case 27: //	noun.substance
					
				case 28: //	noun.time
				
			}
			
		}
		
	}
}
