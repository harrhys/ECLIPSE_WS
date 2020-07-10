package com.farbig.practice.entity.relations;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


@MappedSuperclass
//This needs to be changed to Entity and test for UNIJTO2OTrip...it throws WalkingException

@Table(name = "TRIP_JT")

@DiscriminatorColumn(name = "TRIP_TYPE")

@DiscriminatorValue("UNI_JT_ALL")

public class JTTrip extends Trip {
	
	

}
