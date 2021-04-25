package com.scb.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;

    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "surname is mandatory")
    private String surname;

    @NotBlank(message = "dateOfBirth is mandatory")
    private String dateOfBirth;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,orphanRemoval=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<OrderBook> orderBooks;

}
