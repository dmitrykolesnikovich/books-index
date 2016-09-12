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

@Stateless
@Path("book")
public class BookService {

  @PersistenceContext
  private EntityManager entityManager;

  @GET
  @Produces(MediaType.APPLICATION_XML)
  @Path("list")
  public BookList getBooksList(@NotNull @QueryParam("query") String query) {
    BookList result = new BookList();
    List<Book> books = entityManager.createNamedQuery("books.findByAuthorOrTitle").setParameter("query", query + "%").getResultList();
    result.getBook().addAll(books);
    return result;
  }

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces(MediaType.APPLICATION_XML)
  @Path("save")
  public Response save(Book book) {
    try {
      entityManager.persist(book);
      return Response.status(Response.Status.OK).build();
    } catch (Throwable e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

}
