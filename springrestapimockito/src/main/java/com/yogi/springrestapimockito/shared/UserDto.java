package com.yogi.springrestapimockito.shared;
import jakarta.persistence.Entity;
import lombok.Data;

@Data

public class UserDto {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encyptedPassword;


}
