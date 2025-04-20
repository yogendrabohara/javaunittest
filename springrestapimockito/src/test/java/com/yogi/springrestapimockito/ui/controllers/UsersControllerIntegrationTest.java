package com.yogi.springrestapimockito.ui.controllers;

import com.yogi.springrestapimockito.ui.controllers.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8081")

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8083", "hostname=192.168.0.2"})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@TestPropertySource(locations = "application-test.properties", properties = "server.port=8084")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestRestTemplate testRestTemplate;

    static String jwtToken;


//    @Test
//    void contextLoads() {
//        System.out.println("server.port=" +  serverPort);
//        System.out.println("local server.port=" +  localServerPort);
//
//    }

    @Test
    @DisplayName("User cam be created")
    @Order(1)
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() throws JSONException {
        //Arrange
//        String createdUserJson = "{"
//                + "\"firstName\":\"Yogi\","
//                + "\"lastName\":\"Bohara\","
//                + "\"email\":\"yogi@example.com\","
//                + "\"password\":\"123456789\","
//                + "\"repeatPassword\":\"123456789\""
//                + "}";

        //creating json object
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName",  "yogi");
        userDetailsRequestJson.put("lastName", "bohara");
        userDetailsRequestJson.put("email", "test@test.com");
        userDetailsRequestJson.put("password", "12345678");
        userDetailsRequestJson.put("repeatPassword", "123456789");


//        //create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);
//
//
//        //Act
//        ResponseEntity<UserRest> createdUserDetailsEntity = testRestTemplate.postForEntity("/users", request, UserRest.class);
//        UserRest createdUserDetails = createdUserDetailsEntity.getBody();
//
//        //Assert
//        Assertions.assertEquals(HttpStatus.OK, createdUserDetailsEntity.getStatusCode());
//        Assertions.assertEquals(userDetailsRequestJson.get("firstName"),
//                createdUserDetails.getFirstName(),
//                "Returned user's first name seems to be incorrect");
//
//        Assertions.assertEquals(userDetailsRequestJson.get("lastName"),
//                createdUserDetails.getLastName(),
//                "Returned user's last name seems to be incorrect");
//
//
//        Assertions.assertEquals(userDetailsRequestJson.get("email"),
//                createdUserDetails.getEmail(),
//                "Returned user's email seems to be incorrect");

//        Assertions.assertFalse(userDetailsRequestJson.getUserId().trim().isEmpty,
//                "User id should not be empty");

        //Act
        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userDetailsRequestJson.toString())
                .exchange();



        //Assert
        responseSpec.expectStatus().isOk()
                .expectBody(UserRest.class)
                .value(createdUserDetails -> {
                    Assertions.assertEquals("yogi", createdUserDetails.getFirstName(), "Returned user's first name seems to be incorrect");
                    Assertions.assertEquals("bohara", createdUserDetails.getLastName(), "Returned user's last name seems to be incorrect");
                    Assertions.assertEquals("test@test.com", createdUserDetails.getEmail(), "Returned email seems to be incorrect");
                    Assertions.assertNotNull(createdUserDetails.getUserId(), "User Id should not be null");
                    Assertions.assertFalse(createdUserDetails.getUserId().trim().isEmpty(), "User Id should not be empty");
                });
    }


    @Test
    @DisplayName("Get /users requires JWT")
    @Order(4)
    void testGetUsers_whenMissingJWT_returns403() {
        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity requestEntity = new HttpEntity(null, headers);


        //Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "HTTP status code 403 Forbidden should have been returned");
    }


    //using webtestclient

//    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//    @AutoConfigureWebTestClient
//    class UsersControllerTest {
//
//        @Autowired
//        private WebTestClient webTestClient;
//
//        @Test
//        @DisplayName("Get /users requires JWT")
//        void testGetUsers_whenMissingJWT_returns403() {
//            webTestClient.get()
//                    .uri("/users")
//                    .accept(MediaType.APPLICATION_JSON)
//                    // no Authorization header = simulate missing JWT
//                    .exchange()
//                    .expectStatus().isForbidden()
//                    .expectBody()
//                    .jsonPath("$.message").isEqualTo("Forbidden"); // Adjust path based on your error response
//        }
//    }



    @Test
    @DisplayName("/login works")
    @Order(2)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTinAuthorizationHeader() throws JSONException {
        //Arrange
//        String loginCredentialsJson = "{\n" +
//                "   \"email\":\"test@test.com\",\n" +
//                "   \"password\":\"12345678\"\n" +
//                "}";


        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");


        //Act
        WebTestClient.ResponseSpec responseSpec = webTestClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(loginCredentials.toString())
                .exchange();

        //Assert
        responseSpec.expectStatus().isOk()
                .expectHeader().exists(HttpHeaders.AUTHORIZATION)
                .expectBody(UserRest.class)
                .value(user -> {
                    Assertions.assertNotNull(user.getEmail(), "Email should not be null");
                    Assertions.assertEquals("test@test.com", user.getEmail(), "Email should match the login input");;

                });

        //capture JWT for next test
        jwtToken = responseSpec.returnResult(String.class)
                .getResponseHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        Assertions.assertNotNull(jwtToken, "JWT token should not be null");
    }

    @Test
    @DisplayName("Get /users with valid jwt return 200")
    @Order(3)
    void testGetUsers_whenValidJWTProvided_returns200(){
        webTestClient.get()
                .uri("/users")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserRest.class)
                .consumeWith(response -> {
                    List<UserRest> users = response.getResponseBody();
                    Assertions.assertNotNull(users, "Response body should not be null");
                    Assertions.assertFalse(users.isEmpty(), "User list should not be empty");

                });


    }

    @Test
    @Order(5)
    @DisplayName("Login and fetch users list with JWT")
    void testLoginAndFetchUsersListWithJWT() throws JSONException {
        // Step 1: Login and get JWT
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "test@gmail.com");
        loginJson.put("password", "12345678");

        WebTestClient.ResponseSpec loginResponse = webTestClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginJson.toString())
                .exchange();

        // Validate login success and get JWT
        loginResponse.expectStatus().isOk()
                .expectHeader().exists(HttpHeaders.AUTHORIZATION);

        String jwt = loginResponse.returnResult(UserRest.class)
                .getResponseHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        Assertions.assertNotNull(jwt, "JWT token should not be null");

        // Step 2: Use JWT to fetch /users list
        webTestClient.get()
                .uri("/users")
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserRest.class)
                .value(users -> {
                    Assertions.assertNotNull(users, "User list should not be null");
                    Assertions.assertFalse(users.isEmpty(), "User list should not be empty");
                });
    }


}
