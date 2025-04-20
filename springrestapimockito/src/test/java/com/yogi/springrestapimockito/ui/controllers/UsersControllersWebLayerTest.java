package com.yogi.springrestapimockito.ui.controllers;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogi.springrestapimockito.service.UsersService;
import com.yogi.springrestapimockito.shared.UserDto;
import com.yogi.springrestapimockito.ui.controllers.request.UserDetailsRequestModel;
import com.yogi.springrestapimockito.ui.controllers.response.UserRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.mock.mockito.MockBean;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;


@WebMvcTest(controllers = UsersController.class,
excludeAutoConfiguration = SecurityAutoConfiguration.class)
//@AutoConfigureMockMvc(addFilters = false)
public class UsersControllersWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;



    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails () throws Exception {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Yogi");
        userDetailsRequestModel.setLastName("Boh");
        userDetailsRequestModel.setEmail("yog@gmail.com");
        userDetailsRequestModel.setPassword("123456789");
        userDetailsRequestModel.setRepeatPassword("123456789");

        //for service layer
       // UserDto userDto = new UserDto();
//        userDto.setFirstName("Yogi");
//        userDto.setLastName("Boh");
//        userDto.setEmail("email");
//        userDto.setUserId(UUID.randomUUID().toString());
    UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
    userDto.setUserId(UUID.randomUUID().toString());
    when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));



        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

        //Assert
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(),
                createdUser.getFirstName(), "The returned user first name is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), createdUser.getLastName(),
                "Last name did not exists");
        Assertions.assertEquals(userDetailsRequestModel.getEmail(),
                createdUser.getEmail(),
                "Email does not exists");
        Assertions.assertFalse(createdUser.getUserId().isEmpty());

    }

    @Test
    @DisplayName("First name is not empty")
    void testCreateUser_whenFirstNameIsNotProvided_returns400StatusCode() throws Exception {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("");
        userDetailsRequestModel.setLastName("Boh");
        userDetailsRequestModel.setEmail("test@gmail.com");
        userDetailsRequestModel.setPassword("123456789");
        userDetailsRequestModel.setRepeatPassword("123456789");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));


        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus()
                ,"Incorrect HTTP status code returned");
    }

    @Test
    @DisplayName("firstName is shorter than the minimum size")
    void testCreateUser_whenFirstNameIsOnlyOneCharacter_returns400StatusCode() throws Exception {
        //Arrange


        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("a");
//        userDetailsRequestModel.setLastName("Boh");
//        userDetailsRequestModel.setEmail("test@gmail.com");
//        userDetailsRequestModel.setPassword("123456789");
//        userDetailsRequestModel.setRepeatPassword("123456789");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),
                "First name should have minimum length of 2");

    }


    //Testing list of users
    @Test
    @DisplayName("List of Users")
    void testGetUsers_whenUsersExist_returnsListOfUsers() throws Exception {
        // Arrange
        UserDto user1 = new UserDto();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setFirstName("Yogi");
        user1.setLastName("Boh");
        user1.setEmail("yogi@example.com");

        UserDto user2 = new UserDto();
        user2.setUserId(UUID.randomUUID().toString());
        user2.setFirstName("Alex");
        user2.setLastName("Smith");
        user2.setEmail("alex@example.com");

        when(usersService.getUsers(0, 2)).thenReturn(List.of(user1, user2));

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("page", "0")
                        .param("limit", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        UserRest[] users = mapper.readValue(jsonResponse, UserRest[].class);

        // Assert
        Assertions.assertEquals(2, users.length);
        Assertions.assertEquals("Yogi", users[0].getFirstName());
        Assertions.assertEquals("alex@example.com", users[1].getEmail());
    }


}
