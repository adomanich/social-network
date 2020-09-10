package com.example.demo.config;

import com.example.demo.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {UserRepository.class})
@EntityScan(basePackageClasses = {User.class})
@ComponentScan(basePackages = {"demo"})
@EnableAutoConfiguration
@org.springframework.context.annotation.Configuration
public class Configuration {
}
