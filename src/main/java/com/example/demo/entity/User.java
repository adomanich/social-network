package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@ToString
@Table("Users")
public class User {

    @PrimaryKey
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<Long> following;
    private List<Long> followers;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
