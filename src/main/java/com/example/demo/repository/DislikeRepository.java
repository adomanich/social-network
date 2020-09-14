package com.example.demo.repository;

import com.example.demo.entity.Dislike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikeRepository extends MongoRepository<DislikeRepository, Integer> {
    @Query(value = "{id: ?0}")
    Dislike getDislikedBy(Long id);
}
