package com.dmitrykolesnikovich.booksIndex.webclient;

import com.dmitrykolesnikovich.booksIndex.model.Book;
import com.dmitrykolesnikovich.booksIndex.model.BookList;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Stateless
public class BooksManager {

  public static final String BOOKS_INDEX_SERVICE_URI = "http://localhost:8080/books-index/api/book";
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  private Client client;

  public BooksManager() {
    client = ClientBuilder.newClient();
  }

  @PreDestroy
  public void onDestroy() {
    client.close();
  }

  public List<Book> getBookList() {
    return getBookList("");
  }

  public List<Book> getBookList(String query) {
    BookList bookList = client.target(BOOKS_INDEX_SERVICE_URI).path("list").
        queryParam("query", query).request(MediaType.APPLICATION_XML).get(BookList.class);
    return bookList.getBook();
  }

  public String save(Book book) {
    Response response = null;
    try {
      Entity<Book> entity = Entity.xml(book);
      response = client.target(BOOKS_INDEX_SERVICE_URI).path("save").
          request(MediaType.APPLICATION_XML).post(entity);
      if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        return SUCCESS;
      } else {
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
