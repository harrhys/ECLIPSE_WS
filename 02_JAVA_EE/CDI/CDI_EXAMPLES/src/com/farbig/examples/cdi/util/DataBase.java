/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.cdi.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.farbig.examples.cdi.qualifier.UserEM;
import com.farbig.examples.cdi.qualifier.UserEM.Type;

public class DataBase {
  @Produces
  @PersistenceContext(unitName = "JPA_JTA_DS_ECLIPSELINK")
  @UserEM(Type.ECLIPSELINK)
  EntityManager userDatabaseEclipseLinkEntityManager;
  
  @Produces
  @PersistenceContext(unitName = "JPA_JTA_DS_ECLIPSELINK")
  @UserEM(Type.HIBERNATE)
  EntityManager userDatabaseHibernateEntityManager;
}