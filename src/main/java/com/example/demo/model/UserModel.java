package com.example.demo.model;

import com.example.demo.listener.UserModelListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.servlet.annotation.WebListener;

@Getter
@Setter
@ToString
@Document(collection = "Users")
public class UserModel {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
