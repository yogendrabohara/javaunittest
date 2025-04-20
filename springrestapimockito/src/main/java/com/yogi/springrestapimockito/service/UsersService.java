package com.yogi.springrestapimockito.service;

import com.yogi.springrestapimockito.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto user) throws Exception;
    List<UserDto> getUsers(int page, int limit);
    UserDto getUser(String email);


}
