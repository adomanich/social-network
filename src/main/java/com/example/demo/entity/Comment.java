package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Comment")
public class Comment {

    private String creationDate;
    private String commentText;
    private Long userId;
    private Long publicationId;
}
