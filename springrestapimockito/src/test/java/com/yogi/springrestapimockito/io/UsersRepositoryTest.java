package com.yogi.springrestapimockito.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity(){
        //Arrange
        UserEntity user = new UserEntity();

        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName("Yogi");
        user.setLastName("bohara");
        user.setEmail("test@test.com");
        user.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user);


        //Act
        UserEntity storedUser = usersRepository.findByEmail(user.getEmail());




        //Assert
        Assertions.assertEquals(user.getEmail(), storedUser.getEmail(),
                "The returned email address does not match the expected value");

    }

    @Test
    void testFindByUserId_whenProvidedCorrectUserId_returnsUserEntity() {
//        Arrange
        UserEntity user = new UserEntity();

        user.setUserId("1");
        user.setFirstName("Yogi");
        user.setLastName("bohara");
        user.setEmail("test@test.com");
        user.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user);


        //Act
        UserEntity storedUser = usersRepository.findByUserId(user.getUserId());

        //Assert
        Assertions.assertNotNull(storedUser, "UserEntity should not be null");
        Assertions.assertEquals(user.getUserId(), storedUser.getUserId(),
                "The returned user id does not match");




    }

    @Test
    void testFindUsersWithEmailEndingWith_whenGiveEmailDomain_returnsUsersWithGivenDomain () {

        //Arrange
        UserEntity user = new UserEntity();

        user.setUserId("1");
        user.setFirstName("Yogi");
        user.setLastName("bohara");
        user.setEmail("test@gmail.com");
        user.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user);


        String emailDomainName = "@gmail.com";



        //Act
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomainName);



        //Assert
        Assertions.assertEquals(1, users.size(), "There should be only one user right now for test purpose in the list");
        Assertions.assertTrue(users.get(0).getEmail().endsWith(emailDomainName));
    }
}
