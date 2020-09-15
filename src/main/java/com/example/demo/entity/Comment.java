package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Comment {

    private Long commentId;
    private Long userId;
    private String commentText;
    private Like likes;
    private Dislike dislikes;

    public Comment() {
        commentId = System.currentTimeMillis();
    }
}
