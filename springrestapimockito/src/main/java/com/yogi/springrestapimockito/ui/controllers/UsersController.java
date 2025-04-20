package com.yogi.springrestapimockito.ui.controllers;


import com.yogi.springrestapimockito.service.UsersService;
import com.yogi.springrestapimockito.shared.UserDto;
import com.yogi.springrestapimockito.ui.controllers.request.UserDetailsRequestModel;
import com.yogi.springrestapimockito.ui.controllers.response.UserRest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public UserRest createUser(@RequestBody @Valid UserDetailsRequestModel userDetails) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = new ModelMapper().map(userDetails, UserDto.class);

        UserDto createdUser = usersService.createUser(userDto);
        // createdUser.setUserId("");  //use this one to test fail

        return modelMapper.map(createdUser, UserRest.class);
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<UserDto> users = usersService.getUsers(page, limit);

        Type listType = new TypeToken<List<UserRest>>() {
        }.getType();

        return new ModelMapper().map(users, listType);
    }



}
