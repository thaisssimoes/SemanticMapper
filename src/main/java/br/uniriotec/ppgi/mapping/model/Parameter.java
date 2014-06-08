package br.uniriotec.ppgi.mapping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="parameters")
public class Parameter {
	
	@Id 
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "name", nullable=false, unique=true)
	private String name;
	@Column(name = "value", nullable=false)
	private double value;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
