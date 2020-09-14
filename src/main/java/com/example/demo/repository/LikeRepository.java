package com.example.demo.repository;

import com.example.demo.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, Integer> {
    @Query(value = "{id: ?0}")
    Like getLikedBy(Long id);
}
