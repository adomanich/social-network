package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer> {

    @Query(value = "SELECT * FROM Users where email=?0")
    User findUserByEmail(String email);

    @Query(value = "SELECT u FROM Users u where u.email=?0")
    User getUserIdByEmail(String email);

    @Query(value = "SELECT u FROM Users u where u.id=?0")
    User getUserById(Long id);
}
