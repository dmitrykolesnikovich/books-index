package com.dmitrykolesnikovich.booksIndex.services;


import com.dmitrykolesnikovich.booksIndex.model.Book;
import com.dmitrykolesnikovich.booksIndex.model.BookList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Path("book")
public class BookService {

  private static final Logger logger = Logger.getLogger(BookService.class.getCanonicalName());

  @PersistenceContext
  private EntityManager entityManager;

  @GET
  @Produces(MediaType.APPLICATION_XML)
  @Path("list")
  public BookList getBooksList(@NotNull @QueryParam("query") String query) {
    System.out.println("BookService.getBooksList()");
    BookList result = new BookList();
    String queryParamValue = query + "%";
    System.out.println("queryParamValue: " + queryParamValue);
    List<Book> books = entityManager.createNamedQuery("books.findByAuthorOrTitle").setParameter("query", queryParamValue).getResultList();
    System.out.println("books.size: " + books.size());
    result.getBook().addAll(books);
    return result;
  }

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces(MediaType.APPLICATION_XML)
  @Path("save")
  public Response save(Book book) {
    System.out.println("BookService.save");
    try {
      System.out.println("BookService.save #0");
      entityManager.persist(book);
      System.out.println("BookService.save #1");
      return Response.status(Response.Status.OK).build();
    } catch (Throwable e) {
      logger.log(Level.WARNING, e.getMessage());
      System.out.println("BookService.save #2");
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

}
