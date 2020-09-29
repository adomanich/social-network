package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.entity.request.CommentRequest;
import com.example.demo.exeption.notfound.PublicationNotFoundException;
import com.example.demo.exeption.notfound.UserNotFoundException;
import com.example.demo.service.PublicationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PublicationService publicationService;

    @PostMapping("api/user/{userId}/publication/{publicationId}/comment")
    public ResponseEntity<Publication> createComment(@PathVariable("userId") Long userId,
                                                     @PathVariable("publicationId") Long publicationId,
                                                     @RequestBody CommentRequest commentRequest) {

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.leaveComment(user, publication, commentRequest));
    }

    @DeleteMapping("api/user/{userId}/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("userId") Long userId,
                                           @PathVariable("publicationId") Long publicationId,
                                           @PathVariable("commentId") Long commentId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId, userId);
        if (publication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("You are not able to delete comments from not your publication"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(publicationService.deleteComment(publication, commentId));
        }
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable("userId") Long userId,
                                         @PathVariable("publicationId") Long publicationId,
                                         @PathVariable("commentId") Long commentId,
                                         @RequestBody CommentRequest commentRequest) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(publicationService.editComment(user, publication, commentId, commentRequest));
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/comment/{commentId}/dislike")
    public ResponseEntity<?> dislikeComment(@PathVariable("userId") Long userId,
                                            @PathVariable("publicationId") Long publicationId,
                                            @PathVariable("commentId") Long commentId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.dislikeComment(user, publication, commentId);
        if (updatedPublication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("User already dislike that comment"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedPublication);
        }
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/comment/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable("userId") Long userId,
                                         @PathVariable("publicationId") Long publicationId,
                                         @PathVariable("commentId") Long commentId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.likeComment(user, publication, commentId);
        if (updatedPublication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("User already dislike that comment"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedPublication);
        }
    }
}
