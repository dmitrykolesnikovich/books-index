package com.dmitrykolesnikovich.booksIndex.beans;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@Named
@Stateless
public class BooksManager {

  private static final Logger logger = Logger.getLogger(BooksManager.class.getCanonicalName());

  @PersistenceContext
  private EntityManager entityManager;

  public void testDatasource() {
    logger.info("entityManager.open: " + entityManager.isOpen());
  }

}
