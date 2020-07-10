package com.farbig.practice.entity.relations;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TRIP_JC")
@DiscriminatorColumn(name = "TRIP_TYPE")
@DiscriminatorValue("UNI_JC_ALL")
public class JCTrip extends Trip {
	
}
