package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.model.MySynset;

/**
 * Maps Wordnet Synsets associated to the noun.communication supersense
 * to other semantical classes.
 * 
 * @author felipe
 *
 */
public class CommunicationMapper {
	
	/**
	 * Maps noun.communication synsets to the corresponding
	 * semantic type described by Dixon.
	 * 
	 * @param synset
	 */
	public static void mapToSemType(MySynset synset){
		System.out.println("found "+synset.getSupersense().getName());
	}
	
}
