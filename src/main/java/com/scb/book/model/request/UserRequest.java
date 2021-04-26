package com.scb.book.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {
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
}
