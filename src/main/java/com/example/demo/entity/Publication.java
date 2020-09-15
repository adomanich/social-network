package com.example.demo.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@Document(collection = "Publication")
public class Publication {

    @Id
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private List<Comment> comments;
    private Like like;
    private Dislike dislike;
}
