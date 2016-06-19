package com.solvevolve.jpa;

import com.solvevolve.app.entities.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {

  /**
   * Creates a new DAO with a given session provider.
   *
   * @param sessionFactory a session provider
   */
  public UserDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public User findById(Long id) {
    return get(id);
  }

  public User create(User user) {
    return super.persist(user);
  }


  public User findByPhoneNumber(String phoneNumber) {
    Query query = namedQuery("findUserByPhoneNumber");
    query.setString("phoneNumber", phoneNumber);
    List<User> users = query.list();
    return users.get(0);
  }
}
