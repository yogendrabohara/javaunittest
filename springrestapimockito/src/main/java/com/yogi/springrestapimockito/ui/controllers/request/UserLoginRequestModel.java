package com.yogi.springrestapimockito.ui.controllers.request;


import lombok.Data;

@Data
public class UserLoginRequestModel {
    private String email;
    private String password;

}
