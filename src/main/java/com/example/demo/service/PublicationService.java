package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.request.CommentRequest;
import com.example.demo.entity.request.PublicationRequest;
import com.example.demo.exeption.CommentEditException;
import com.example.demo.exeption.PublicationAlreadyExistException;
import com.example.demo.exeption.UnSuccessDeleteException;
import com.example.demo.exeption.notfound.CommentNotFoundException;
import com.example.demo.mapping.PublicationMapping;
import com.example.demo.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public List<Publication> getUsersPublications(Long userId) {
        return publicationRepository.getUsersPublicationList(userId);
    }

    public Publication getPublication(Long publicationId) {
        return publicationRepository.getPublicationById(publicationId);
    }

    public Publication getPublication(Long publicationId, Long userId) {
        return publicationRepository.getPublicationById(publicationId, userId);
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

    public void deletePublication(User user, Publication publication) {
        publicationRepository.delete(publication);
        List<Publication> userPublication = publicationRepository.getUsersPublicationList(user.getId());
        if (userPublication.stream().anyMatch(p -> p.getId().equals(publication.getId()))) {
            throw new UnSuccessDeleteException("Publication - " + publication.getId() + " was not deleted");
        }
    }

    public Publication editPublication(Publication publication, PublicationRequest publicationRequest) {
        publication.setName(publicationRequest.getName());
        publication.setDescription(publicationRequest.getDescription());
        return publicationRepository.save(publication);
    }

    public Publication likePublication(User user, Publication publication) {
        Publication updatedPublication = null;
        List<Long> userIdWhoLikeAPublication = publication.getLike().getLikedBy();
        if (!userIdWhoLikeAPublication.contains(user.getId())) {
            userIdWhoLikeAPublication.add(user.getId());
            List<Long> userIdWhoDislikesPublication = publication.getDislike().getDislikedBy();
            if (userIdWhoDislikesPublication.contains(user.getId())) {
                userIdWhoDislikesPublication.remove(user.getId());
            }
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    public Publication dislikePublication(User user, Publication publication) {
        Publication updatedPublication = null;
        List<Long> userIdWhoDislikeAPublication = publication.getDislike().getDislikedBy();
        if (!userIdWhoDislikeAPublication.contains(user.getId())) {
            userIdWhoDislikeAPublication.add(user.getId());

            List<Long> userIdWhoLikeAPublication = publication.getLike().getLikedBy();
            if (userIdWhoLikeAPublication.contains(user.getId())) {
                userIdWhoLikeAPublication.remove(user.getId());
            }
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    public Publication leaveComment(User user, Publication publication, CommentRequest commentRequest) {
        List<Comment> publicationComments = publication.getComments();
        Comment comment = new Comment();
        comment.setCommentText(commentRequest.getText());
        comment.setUserId(user.getId());
        comment.setLikes(new Like());
        comment.setDislikes(new Dislike());
        publicationComments.add(comment);
        return publicationRepository.save(publication);
    }

    public Publication deleteComment(Publication publication, Long commentId) {
        List<Comment> publicationComments = publication.getComments();

        Comment comment = publicationComments.stream()
                .filter(c -> c.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Comment with " + commentId + " id does not exist"));

        publicationComments.remove(comment);
        return publicationRepository.save(publication);
    }

    public Publication editComment(User user, Publication publication, Long commentId, CommentRequest commentRequest) {

        List<Comment> publicationComments = publication.getComments();
        Comment comment = publicationComments.stream()
                .filter(c -> c.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Comment with " + commentId + " id not found"));

        if (comment.getUserId() == user.getId()) {
            comment.setCommentText(commentRequest.getText());
            return publicationRepository.save(publication);
        } else {
            throw new CommentEditException("User can edit only his comment");
        }
    }

    public Publication likeComment(User user, Publication publication, Long commentId) {
        Publication updatedPublication = null;
        List<Long> userIdWhoLikeComment = getUserWhoLikeComment(publication, commentId);

        if (!userIdWhoLikeComment.contains(user.getId())) {
            userIdWhoLikeComment.add(user.getId());

            List<Long> userIdWhoDislikeAPublication = getUserWhoDislikeComment(publication, commentId);
            if (userIdWhoDislikeAPublication.contains(user.getId())) {
                userIdWhoDislikeAPublication.remove(user.getId());
            }
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    public Publication dislikeComment(User user, Publication publication, Long commentId) {
        Publication updatedPublication = null;
        List<Long> userIdWhoDislikeComment = getUserWhoDislikeComment(publication, commentId);

        if (!userIdWhoDislikeComment.contains(user.getId())) {
            userIdWhoDislikeComment.add(user.getId());

            List<Long> userIdWhoLikeAPublication = getUserWhoLikeComment(publication, commentId);
            if (userIdWhoLikeAPublication.contains(user.getId())) {
                userIdWhoLikeAPublication.remove(user.getId());
            }
            updatedPublication = publicationRepository.save(publication);
        }
        return updatedPublication;
    }

    private List<Long> getUserWhoDislikeComment(Publication publication, Long commentId) {
        return publication.getComments()
                .stream()
                .filter(comment -> comment.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Comment with " + commentId + " id not found"))
                .getDislikes()
                .getDislikedBy();
    }

    private List<Long> getUserWhoLikeComment(Publication publication, Long commentId) {
        return publication.getComments()
                .stream()
                .filter(comment -> comment.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Comment with " + commentId + " id not found"))
                .getLikes()
                .getLikedBy();
    }
}
