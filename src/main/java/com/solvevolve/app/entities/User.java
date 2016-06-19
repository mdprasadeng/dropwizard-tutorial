package com.solvevolve.app.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@NamedQueries(
    {
        @NamedQuery(
            name = "findUserByPhoneNumber",
            query = "Select u from User u where u.phoneNumber = :phoneNumber"
        )
    }
)
@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;
}
