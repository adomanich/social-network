package com.example.demo.dto;

import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private User user;
    private List<User> subscribers;
    private List<User> followers;
    private List<Publication> publications;
}
