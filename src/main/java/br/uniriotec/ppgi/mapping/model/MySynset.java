package br.uniriotec.ppgi.mapping.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.mit.jwi.item.POS;

/**
 * Model class representing a Wordnet Synset along with its
 * main attributes. It always relates to a Supersense and it may
 * relates to a SemanticType if the mapping has already been 
 * conducted, otherwise the SemanticType is NULL.
 * 
 * @author felipe
 *
 */
@Entity
@Table(name="synsets")
public class MySynset {
	@Id 
	@Column(name = "wordnetID", nullable=false, unique=true)
	private String wordnetID;
	@Enumerated(EnumType.STRING)
	private POS pos;
	@Column(name = "gloss", columnDefinition="TEXT", nullable=false)
	private String gloss;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_supersense", nullable = false)
	private Supersense supersense;
	@ManyToMany(cascade = { CascadeType.ALL })  
	@JoinTable(name = "synset_has_semtype", 
		joinColumns = { @JoinColumn(name = "wordnet_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "id_semtype") })
	private Set<SemanticType> semanticTypes = new HashSet<SemanticType>();
	@ElementCollection
	@CollectionTable(name="words", joinColumns=@JoinColumn(name="id_synset"))
	@Column(name="words", nullable=false)
	private List<String> words = new ArrayList<String>();
	
	public MySynset(){}
	
	public String getWordnetID() {
		return wordnetID;
	}
	public void setWordnetID(String wordnetID) {
		this.wordnetID = wordnetID;
	}
	public POS getPos() {
		return pos;
	}
	public void setPos(POS pos) {
		this.pos = pos;
	}
	public String getGloss() {
		return gloss;
	}
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}
	public Supersense getSupersense() {
		return supersense;
	}
	public void setSupersense(Supersense supersense) {
		this.supersense = supersense;
	}
	public Set<SemanticType> getSemanticType() {
		return semanticTypes;
	}
	public void setSemanticType(Set<SemanticType> semanticType) {
		this.semanticTypes = semanticType;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	

	
}
