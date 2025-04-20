package com.yogi.springrestapimockito.ui.controllers.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetailsRequestModel {
    @Size(min=2, message = "First name must not be less than 2 characters")
    private String firstName;
    @Size(min=2, message = "Last name must not be less than 2 characters")
    private String lastName;
    @Email
    private String email;

    @Size(min=8, max=16, message = "First name must not be less than 2 characters")
    private String password;
    @Size(min=2, max=16, message = "First name must not be less than 2 characters")
    private String repeatPassword;
}
