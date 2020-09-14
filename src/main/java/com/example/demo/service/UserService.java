package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exeption.FollowerNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User model) {
        model.setId(System.currentTimeMillis());
        return userRepository.save(model);
    }

    public User startFollowing(User userStartFollowing, User userBeFollowed) {
        List<Long> currentFollowings = userStartFollowing.getFollowing();
        currentFollowings.add(userBeFollowed.getId());
        userStartFollowing.setFollowing(currentFollowings);

        List<Long> currentFollowersOfUserBeFollowed = userBeFollowed.getFollowers();
        currentFollowersOfUserBeFollowed.add(userStartFollowing.getId());
        userBeFollowed.setFollowers(currentFollowersOfUserBeFollowed);

        userRepository.save(userStartFollowing);
        userRepository.save(userBeFollowed);

        return userStartFollowing;
    }

    public User removeFollower(User user, User follower) {
        removeFollower(user, follower.getId());
        removeFollowing(follower, user.getId());
        userRepository.save(follower);
        return userRepository.save(user);
    }

    public User removeFollowing(User user, User following) {
        removeFollowing(user, following.getId());
        removeFollower(following, user.getId());
        userRepository.save(following);
        return userRepository.save(user);
    }

    private void removeFollower(User user, Long id) {
        user.getFollowers().remove(user.getFollowers()
                .stream()
                .filter(followerId -> followerId.equals(id))
                .findFirst()
                .orElseThrow(() -> new FollowerNotFoundException(id + " user is not a follower for " + user.getId() + " user")));
    }

    private void removeFollowing(User user, Long id) {
        user.getFollowing().remove(user.getFollowing()
                .stream()
                .filter(followingId -> followingId.equals(id))
                .findFirst()
                .orElseThrow(() -> new FollowerNotFoundException(id + " user is not a following for " + user.getId() + " user")));
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserIdByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
