package com.yogi.springrestapimockito.ui.controllers.response;


import lombok.Data;


@Data
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
