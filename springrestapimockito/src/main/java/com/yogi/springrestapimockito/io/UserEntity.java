package com.yogi.springrestapimockito.io;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.io.Serializable;


@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID=5313493413859894403L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Column(nullable = false, length = 50)

    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 120)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;


}

