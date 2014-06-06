package br.uniriotec.ppgi.mapping.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The class serves as the relational entity between a MySynset
 * object and a SemanticType object. It is necessary because otherwise
 * it would not be possible to add a attribute to the mapping.
 * By default when a mapping is created it is considered real one.
 * 
 * @author felipe
 *
 */
@Entity
@Table(name = "synset_has_semtype")
@AssociationOverrides({
        @AssociationOverride(name = "pk.synset",
            joinColumns = @JoinColumn(name = "id_synset")),
        @AssociationOverride(name = "pk.semtype",
            joinColumns = @JoinColumn(name = "id_semtype")) })
public class MySynsetSemtype {
	
	@EmbeddedId
	private MySynsetSemtypeID pk = new MySynsetSemtypeID();
	@Column(name="isRealMapping" ,updatable=false)
	private boolean realMapping = true;
	
	
	
	public MySynsetSemtypeID getPk() {
		return pk;
	}
	public void setPk(MySynsetSemtypeID pk) {
		this.pk = pk;
	}
	public boolean isRealMapping() {
		return realMapping;
	}
	public void setRealMapping(boolean realMapping) {
		this.realMapping = realMapping;
	}
	
	@Transient
    public MySynset getMySynset() {
        return getPk().getSynset();
    }
 
    public void setMySynset(MySynset synset) {
        getPk().setSynset(synset);
    }
 
    @Transient
    public SemanticType getSemanticType() {
        return getPk().getSemtype();
    }
 
    public void setSemantictype(SemanticType semtype) {
        getPk().setSemtype(semtype);
    }

	
	@Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MySynset))
            return false;
        MySynsetSemtype other = (MySynsetSemtype) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        if (realMapping == false) {
            if (other.realMapping != false)
                return false;
        } else if (!realMapping == other.realMapping)
            return false;
        return true;
    }


	
}
