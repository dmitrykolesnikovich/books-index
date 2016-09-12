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
import java.io.Serializable;
import java.util.List;

@Named
@Stateless
public class BooksManager implements Serializable {

  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  private static final long serialVersionUID = 1;
  private Client client;
  public static final String BOOKS_INDEX_SERVICE_URI = "http://localhost:8080/books-index/api/book";

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
    BookList bookList = client.target(BOOKS_INDEX_SERVICE_URI).path("list").queryParam("query", query).request(MediaType.APPLICATION_XML).get(BookList.class);
    System.out.println("BooksManager.getBookList, bookList.size: " + bookList.getBook().size());
    return bookList.getBook();
  }

  public String save(Book book) {
    Response response = null;
    System.out.println("BooksManager.save: " + this);
    try {
      System.out.println("book: " + book.toString());
      Entity<Book> entity = Entity.xml(book);
      System.out.println("entity: " + entity);
      response = client.target("http://localhost:8080/books-index/api").path("/book/save").request(MediaType.APPLICATION_XML).post(entity);
      System.out.println("status: " + response.getStatus());
      System.out.println(SUCCESS);
      if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        return SUCCESS;
      } else {
        return FAIL;
      }
    } catch (ResponseProcessingException e) {
      e.printStackTrace();
      System.out.println(FAIL);
      return FAIL;
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }

}
