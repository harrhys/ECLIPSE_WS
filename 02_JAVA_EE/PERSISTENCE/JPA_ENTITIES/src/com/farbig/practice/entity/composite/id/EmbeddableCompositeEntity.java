package com.farbig.practice.entity.composite.id;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.farbig.practice.entity.BaseEntity;


@Entity
@Table(name= "COMPOSITE_ENTITY")
public class EmbeddableCompositeEntity extends BaseEntity {
	
	@EmbeddedId
	private EmbeddableCompositeId id;
	
	private String c;
	
	public EmbeddableCompositeId getId() {
		return id;
	}

	public void setId(EmbeddableCompositeId id) {
		this.id = id;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
