package com.yogi.springrestapimockito.io;


import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserEntityIntegrationTest {


    @Autowired
    private TestEntityManager testEntityManager;  //It wraps the standard JPA EntityManager and provides utility methods to persist and retrieve entities easily during unit or integration testing

    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Yogi");
        userEntity.setLastName("bohara");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");

    }
    @Test
    @Order(1)
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails() {
        //Arrange


        //Act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);

        //Assert
        Assertions.assertTrue(storedUserEntity.getId() > 0);
        Assertions.assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        Assertions.assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        Assertions.assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());
    }


    @Test
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        //Arrange
        userEntity.setFirstName("alglajielkggjljaogltglatirtoglatooputoohgfolaglhatolglgtoietoghahtitolgotogolothooglotho");



//        //Act
//        testEntityManager.persistAndFlush(userEntity);


        //Assert & Act

        assertThrows(PersistenceException.class,  () -> {
            testEntityManager.persistAndFlush(userEntity);

        }, "Was expecting a PersistanceException to be thrown.");
    }


    @Test
    void testUserEntity_whenUserIdIsNotUnique_shouldThrowException() {
        //Arrange
        UserEntity newEntity = new UserEntity();
        newEntity.setUserId("1");
        newEntity.setFirstName("test");
        newEntity.setLastName("one");
        newEntity.setEmail("testone@test.com");
        newEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(newEntity);

        //update existing user entity with the same user id
        userEntity.setUserId("1");

        //Act & Assert
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(userEntity);
        }, "Expected PresistanceException to be thrown here");

    }




}
