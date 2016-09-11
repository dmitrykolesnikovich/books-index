package com.dmitrykolesnikovich.booksIndex.beans;

import com.dmitrykolesnikovich.booksIndex.model.Book;

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

    // add book to database
    Book book = new Book();
    book.setAuthor("Ivan Turgenev");
    book.setTitle("Fathers and Sons");
    book.setIsbn("0-199-53604-9");
    entityManager.persist(book);
  }

}
