package com.example.demo.mapping;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

import java.util.ArrayList;

public class UserMappingDto {


    public static UserDto getInitialUserDto(User user) {
        return UserDto.builder()
                .user(user)
                .followers(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .publications(new ArrayList<>())
                .build();
    }
}
