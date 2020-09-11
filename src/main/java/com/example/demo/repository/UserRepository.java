package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    @Query("{email: ?0}")
    User findUserByEmail(String email);

    @Query(value = "{email: ?0}", fields = "{password: 1}")
    User getUserPasswordByEmail(String email);

    @Query(value = "{email: ?0}")
    User getUserIdByEmail(String email);
}
