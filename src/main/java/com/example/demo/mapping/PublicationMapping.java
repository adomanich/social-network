package com.example.demo.mapping;

import com.example.demo.entity.Dislike;
import com.example.demo.entity.Like;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.entity.request.PublicationRequest;

import java.util.ArrayList;

public class PublicationMapping {

    public static Publication getPublication(User user, PublicationRequest publicationRequest) {
        return Publication.builder()
                .comments(new ArrayList<>())
                .name(publicationRequest.getName())
                .description(publicationRequest.getDescription())
                .like(new Like())
                .dislike(new Dislike())
                .userId(user.getId())
                .id(System.currentTimeMillis())
                .build();
    }
}
