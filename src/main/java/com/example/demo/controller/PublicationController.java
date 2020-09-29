package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Message;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.entity.request.PublicationRequest;
import com.example.demo.exeption.notfound.PublicationNotFoundException;
import com.example.demo.exeption.notfound.UserNotFoundException;
import com.example.demo.mapping.UserMappingDto;
import com.example.demo.service.PublicationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("api/user/{userId}/publication/{publicationId}")
    public ResponseEntity<?> editPublication(@PathVariable("userId") Long userId,
                                             @PathVariable("publicationId") Long publicationId,
                                             @RequestBody PublicationRequest publicationRequest) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId, userId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(publicationService.editPublication(publication, publicationRequest));
    }

    @DeleteMapping("api/user/{userId}/publication/{publicationId}")
    public ResponseEntity<UserDto> deletePublication(@PathVariable("userId") Long userId,
                                                     @PathVariable("publicationId") Long publicationId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId, userId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        publicationService.deletePublication(user, publication);
        return ResponseEntity.status(HttpStatus.OK).body(UserMappingDto.getUserDto(user, publicationService.getUsersPublications(userId)));
    }

    @PutMapping("api/user/{userId}/publication/{publicationId}/like")
    public ResponseEntity<?> likePublication(@PathVariable("userId") Long userId,
                                             @PathVariable("publicationId") Long publicationId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.likePublication(user, publication);
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
        Publication publication = publicationService.getPublication(publicationId);
        if (publication == null) {
            throw new PublicationNotFoundException("Publication with " + publicationId + " id not found");
        }
        Publication updatedPublication = publicationService.dislikePublication(user, publication);
        if (updatedPublication == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("User already dislike that publication"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedPublication);
        }
    }

    @GetMapping("api/user/{userId}/publications")
    public ResponseEntity<List<Publication>> getUserPublications(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with " + userId + " id not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(publicationService.getUsersPublications(userId));
    }
}
