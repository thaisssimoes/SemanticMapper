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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "wordnetID", nullable=false)
	private String wordnetID;
	@Enumerated(EnumType.STRING)
	private POS pos;
	@Column(name = "gloss", columnDefinition="TEXT", nullable=false)
	private String gloss;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_supersense", nullable = false)
	private Supersense supersense;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.synset", cascade=CascadeType.ALL)
	private Set<MySynsetSemtype> mySynsetSemtype = new HashSet<MySynsetSemtype>();
	@ElementCollection
	@CollectionTable(name="words", joinColumns=@JoinColumn(name="id_synset"))
	@Column(name="word", nullable=false)
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
	public Set<MySynsetSemtype> getMySynsetSemtype() {
		return mySynsetSemtype;
	}
	public void setMySynsetSemtypee(Set<MySynsetSemtype> mySynsetSemtype) {
		this.mySynsetSemtype = mySynsetSemtype;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	
	/**
	 * relates the synset to a SemanticType. This method establishes a
	 * real mapping only. For false examples use addSemanticType(SemanticType, boolean);
	 * @param semtype
	 */
	public void addSemanticType(SemanticType semtype) {
		addSemanticType(semtype, true);
	}
	
	
	/**
	 * relates the synset to a SemanticType informing if the mapping
	 * is a real mapping or a false example
	 */
	public void addSemanticType(SemanticType semtype, boolean isRealMapping) {
		if(semtype == null){
			System.out.println(this.getSupersense().getId() + " | "+this.getSupersense().getName());
			System.exit(1);
			}
		MySynsetSemtype relation = new MySynsetSemtype();
		relation.setRealMapping(isRealMapping);
		relation.setMySynset(this);
		relation.setSemantictype(semtype);
		getMySynsetSemtype().add(relation);
		
	}
	

	
}
