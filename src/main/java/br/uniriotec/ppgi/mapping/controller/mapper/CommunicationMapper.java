package br.uniriotec.ppgi.mapping.controller.mapper;

import br.uniriotec.ppgi.mapping.model.MySynset;

public class CommunicationMapper {
	
	public static void mapToSemType(MySynset synset){
		System.out.println("found "+synset.getSupersense().getName());
	}
	
}
