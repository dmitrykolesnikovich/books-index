package com.dmitrykolesnikovich.booksIndex.webclient;

import com.dmitrykolesnikovich.booksIndex.model.Book;
import com.dmitrykolesnikovich.booksIndex.model.BookList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@Stateful
@SessionScoped
public class BooksManager implements Serializable {

  private static final Logger logger = Logger.getLogger(BooksManager.class.getName());

  public static final String BOOKS_INDEX_SERVICE_URI = "http://localhost:8080/books-index/api/book";
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";
  private List<Book> books;

  private Client client;

  public BooksManager() {
    client = ClientBuilder.newClient();
  }

  @PostConstruct
  public void onCreate() {
    search(""); // init on start
  }

  @PreDestroy
  public void onDestroy() {
    client.close();
  }

  public void search(String query) {
    BookList bookList = client.target(BOOKS_INDEX_SERVICE_URI).path("list").
        queryParam("query", query).request(MediaType.APPLICATION_XML).get(BookList.class);
    books = bookList.getBook();
  }

  public List<Book> getBooks() {
    return books;
  }

  public String save(Book book) {
    Entity<Book> entity = Entity.xml(book);
    Response response = null;
    try {
      response = client.target(BOOKS_INDEX_SERVICE_URI).path("save").request(MediaType.APPLICATION_XML).post(entity);
      if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        return SUCCESS;
      } else {
        String errorMessage = (String) response.getEntity();
        logger.warning(errorMessage);

        return FAIL;
      }
    } catch (ResponseProcessingException e) {
      return FAIL;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

}
