package com.example.demo.mapping;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMappingDto {


    public static UserDto getInitialUserDto(User user) {
        return UserDto.builder()
                .user(user)
                .publications(new ArrayList<>())
                .build();
    }

    public static UserDto getUserDto(User user, List<Publication> publications) {
        return UserDto.builder()
                .user(user)
                .publications(publications)
                .build();
    }
}
