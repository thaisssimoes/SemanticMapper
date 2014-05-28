package br.uniriotec.ppgi.mapping.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.mit.jwi.item.POS;

/**
 * Mode class representing a Supersense, meaning a Wordnet Lexical File
 * or Lexname. This object is used to enable relations between MySynset 
 * objects and SemanticType objects.
 * @author felipe
 *
 */
@Entity
@Table(name="supersenses")
public class Supersense {
	
	@Id 
	@Column(name = "id", nullable=false)
	private int id;
	@Enumerated(EnumType.STRING)
	private POS pos;
	@Column(name = "name", nullable=false, unique=true)
	private String name;
	@Column(name = "definition", columnDefinition="TEXT", nullable=false)
	private String definition;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supersense")
	private Set<MySynset> synsets = new HashSet<MySynset>(0);
	
	public Supersense(){}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public POS getPos() {
		return pos;
	}
	public void setPos(POS pos) {
		this.pos = pos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public Set<MySynset> getSynsets() {
		return synsets;
	}
	public void setSynsets(Set<MySynset> synsets) {
		this.synsets = synsets;
	}
	
	
}
