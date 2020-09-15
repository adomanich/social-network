package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Like {
    private List<Long> likedBy;

    public Like() {
        likedBy = new ArrayList<>();
    }
}
