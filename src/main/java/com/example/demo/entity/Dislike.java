package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "Dislike")
public class Dislike {

    @Id
    private Long publicationId;
    private List<Long> dislikedBy;
}
