package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.entity.request.PublicationRequest;
import com.example.demo.exeption.PublicationNotFoundException;
import com.example.demo.exeption.UserNotFoundException;
import com.example.demo.service.PublicationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicationController {

    @Autowired
    private UserService userService;
    @Autowired
    private PublicationService publicationService;

    @PostMapping("api/user/{userId}/publication")
    public ResponseEntity<?> createPublication(@PathVariable("userId") Long userId,
                                               @RequestBody PublicationRequest publicationRequest) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.createPublication(user, publicationRequest));
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/like")
    public ResponseEntity<?> likePublication(@PathVariable("userId") Long userId,
                                             @PathVariable("publicationId") Long publicationId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(userId, publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.likeAPublication(user, publication);
        if (updatedPublication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("User already liked that publication"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedPublication);
        }
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/dislike")
    public ResponseEntity<?> dislikePublication(@PathVariable("userId") Long userId,
                                                @PathVariable("publicationId") Long publicationId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(userId, publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.dislikeAPublication(user, publication);
        if (updatedPublication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("User already dislike that publication"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedPublication);
        }
    }

//    @PutMapping()
//    public ResponseEntity<Publication> leaveComment() {
//    }
//
//    @PutMapping()
//    public ResponseEntity<?> editPublication() {}
//
//    @DeleteMapping()
//    public ResponseEntity<Publication> deleteComment() {
//    }
//
//    @DeleteMapping()
//    public ResponseEntity<?> deletePublication(){}
}
