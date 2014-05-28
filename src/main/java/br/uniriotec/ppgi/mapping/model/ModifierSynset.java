package br.uniriotec.ppgi.mapping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model class referring to a modifier synset, which is a 
 * synset that can appear in the hypernym tree of other synsets.
 * It only exposes the WordnetID for the synset and a symbolic
 * name.
 * 
 * @author felipe
 *
 */
@Entity
@Table(name="modifiers")
public class ModifierSynset {
	@Id 
	@Column(name = "wordnetID", nullable=false)
	private String wordnetID;
	@Column(name = "name", nullable=false, unique=true)
	private String name;
	
	public ModifierSynset(){}

	public String getWordnetID() {
		return wordnetID;
	}

	public void setWordnetID(String wordnetID) {
		this.wordnetID = wordnetID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
