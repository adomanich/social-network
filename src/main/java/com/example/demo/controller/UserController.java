package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.exeption.UserNotFoundException;
import com.example.demo.mapping.UserMappingDto;
import com.example.demo.service.PublicationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PublicationService publicationService;

    @GetMapping("api/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = userService.findAllUsers()
                .stream()
                .map(user -> UserMappingDto.getUserDto(user, publicationService.getUsersPublications(user.getId())))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PutMapping("/api/user/{userId}")
    public ResponseEntity<UserDto> startFollow(@RequestParam("followerId") Long followerId,
                                               @PathVariable("userId") Long userId) {

        User userToUpdate = userService.getUserById(userId);
        if (userToUpdate == null) {
            throw new UserNotFoundException("User with " + userId + " does not exist");
        }

        User userBeFollowed = userService.getUserById(followerId);
        if (userBeFollowed == null) {
            throw new UserNotFoundException("User with " + followerId + " does not exist");
        }

        User updatedUser = userService.startFollowing(userToUpdate, userBeFollowed);
        List<Publication> usersPublications = publicationService.getUsersPublications(updatedUser.getId());

        return ResponseEntity.status(HttpStatus.OK).body(UserMappingDto.getUserDto(updatedUser, usersPublications));
    }

    @DeleteMapping("api/user/{userId}/followers/{followerId}")
    public ResponseEntity<UserDto> removeFollower(@PathVariable("userId") Long userId,
                                                  @PathVariable("followerId") Long followerId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " does not exist");
        }

        User follower = userService.getUserById(followerId);
        if (follower == null) {
            throw new UserNotFoundException("User with " + userId + " does not exist");
        }

        User updatedUser = userService.removeFollower(user, follower);
        List<Publication> usersPublications = publicationService.getUsersPublications(updatedUser.getId());

        return ResponseEntity.status(HttpStatus.OK).body(UserMappingDto.getUserDto(updatedUser, usersPublications));
    }

    @DeleteMapping("api/user/{userId}/following/{followingId}")
    public ResponseEntity<UserDto> removeFollowing(@PathVariable("userId") Long userId,
                                                   @PathVariable("followingId") Long followingId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " does not exist");
        }

        User follower = userService.getUserById(followingId);
        if (follower == null) {
            throw new UserNotFoundException("User with " + userId + " does not exist");
        }

        User updatedUser = userService.removeFollowing(user, follower);
        List<Publication> usersPublications = publicationService.getUsersPublications(updatedUser.getId());

        return ResponseEntity.status(HttpStatus.OK).body(UserMappingDto.getUserDto(updatedUser, usersPublications));
    }
}
