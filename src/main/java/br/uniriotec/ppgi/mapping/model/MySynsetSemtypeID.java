package br.uniriotec.ppgi.mapping.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * This class serves as the ID that establishes a Mapping of a
 * MySynset to a SemanticType. inside this ID object is the atual
 * relation between both objects.
 * 
 * @author felipe
 *
 */
@Embeddable
public class MySynsetSemtypeID implements Serializable {
	private static final long serialVersionUID = -3506726594961588981L;
	
	@ManyToOne
	private MySynset synset;
	@ManyToOne
	private SemanticType semtype;
	
	
	public MySynset getSynset() {
		return synset;
	}
	public void setSynset(MySynset synset) {
		this.synset = synset;
	}
	public SemanticType getSemtype() {
		return semtype;
	}
	public void setSemtype(SemanticType semtype) {
		this.semtype = semtype;
	}
	
	
	@Override
    public int hashCode() {
         int result;
            result = (synset != null ? synset.hashCode() : 0);
            result = 17 * result + (semtype != null ? semtype.hashCode() : 0);
            return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MySynsetSemtypeID))
            return false;
        MySynsetSemtypeID other = (MySynsetSemtypeID) obj;
        if (semtype == null) {
            if (other.semtype != null)
                return false;
        } else if (!semtype.equals(other.semtype))
            return false;
        if (synset == null) {
            if (other.synset != null)
                return false;
        } else if (!synset.equals(other.synset))
            return false;
        return true;
    }

	
}