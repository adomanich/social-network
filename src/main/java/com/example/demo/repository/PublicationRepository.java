package com.example.demo.repository;

import com.example.demo.entity.Publication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends MongoRepository<Publication, Integer> {

    @Query(value = "{userId: ?0}")
    List<Publication> getUsersPublicationList(Long userId);

    @Query(value = "{userId: ?0, publicationId: ?1}")
    Publication getPublicationById(Long userId, Long publicationId);
}
