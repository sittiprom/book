package com.scb.book.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scb.book.domain.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String name ;
    private String surname;
    private String dateOfBirth;
    private List<Integer> books;

    public UserResponse(User user, List books) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirth = user.getDateOfBirth();
        this.books = books;
    }
}

