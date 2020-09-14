package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "Users")
public class User {

    @Id
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<Long> following;
    private List<Long> followers;
}
