package com.example.demo.service;

import com.example.demo.entity.Dislike;
import com.example.demo.entity.Like;
import com.example.demo.entity.Publication;
import com.example.demo.entity.User;
import com.example.demo.entity.request.PublicationRequest;
import com.example.demo.exeption.PublicationAlreadyExistException;
import com.example.demo.mapping.PublicationMapping;
import com.example.demo.repository.DislikeRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private DislikeRepository dislikeRepository;

    public List<Publication> getUsersPublications(Long userId) {
        return publicationRepository.getUsersPublicationList(userId);
    }

    public Publication getPublication(Long userId, Long publicationId) {
        return publicationRepository.getPublicationById(userId, publicationId);
    }

    public Publication createPublication(User user, PublicationRequest publicationRequest) {
        List<Publication> userPublication = publicationRepository.getUsersPublicationList(user.getId());
        boolean isPublicationExist = userPublication.stream()
                .anyMatch(publication -> publication.getName().equals(publicationRequest.getName()));
        if (isPublicationExist) {
            throw new PublicationAlreadyExistException("User already has the publication with \"" + publicationRequest.getName() + " \" name");
        }

        return publicationRepository.save(PublicationMapping.getPublication(user, publicationRequest));
    }

    public Publication likeAPublication(User user, Publication publication) {
        Publication updatedPublication = null;
        List<Long> likes = likeRepository.getLikedBy(publication.getId()).getLikedBy();
        if (!likes.contains(user.getId())) {
            Like like = new Like();
            likes.add(user.getId());
            like.setLikedBy(likes);
            publication.setLike(like);
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    public Publication dislikeAPublication(User user, Publication publication) {
        Publication updatedPublication = null;
        List<Long> dislikes = dislikeRepository.getDislikedBy(publication.getId()).getDislikedBy();
        if (!dislikes.contains(user.getId())) {
            Dislike dislike = new Dislike();
            dislikes.add(user.getId());
            dislike.setDislikedBy(dislikes);
            publication.setDislike(dislike);
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    public List<Long> getUserIdDislikedAPublication(Long publicationId) {
        return dislikeRepository.getDislikedBy(publicationId).getDislikedBy();
    }


}
