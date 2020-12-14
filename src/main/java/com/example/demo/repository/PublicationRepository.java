package com.example.demo.repository;

import com.example.demo.entity.Publication;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends CassandraRepository<Publication, Integer> {

    @Query(value = "SELECT * from Publications WHERE userId=?0")
    List<Publication> getUsersPublicationList(Long userId);

    @Query(value = "SELECT p from Publications p WHERE p.id=?0")
    Publication getPublicationById(Long publicationId);

    @Query(value = "SELECT p from Publications p WHERE p.userId=?0 AND p.id=?1")
    Publication getPublicationById(Long publicationId, Long userId);
}
