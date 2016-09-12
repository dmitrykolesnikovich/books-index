package com.dmitrykolesnikovich.booksIndex.beans;

import javax.ejb.Stateless;
import javax.inject.Named;

// backing bean for search form on list.xhtml
@Stateless
@Named("search")
public class SearchBean {

  private String query;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

}
