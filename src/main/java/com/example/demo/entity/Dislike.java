package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Dislike {

    private List<Long> dislikedBy;

    public Dislike() {
        dislikedBy = new ArrayList<>();
    }
}
