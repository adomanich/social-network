package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exeption.UnSuccessDeleteException;
import com.example.demo.exeption.notfound.FollowerNotFoundException;
import com.example.demo.repository.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepositoryImp userRepositoryImp;
    @Autowired
    private RedisTemplate redisTemplate;

    private final String KEY = "USER";

    public boolean saveUser(User model) {
        model.setId(System.currentTimeMillis());
        return userRepositoryImp.saveUser(model);
    }

    public User startFollowing(User userStartFollowing, User userBeFollowed) {
        List<Long> currentFollowings = userStartFollowing.getFollowing();
        currentFollowings.add(userBeFollowed.getId());
        userStartFollowing.setFollowing(currentFollowings);

        List<Long> currentFollowersOfUserBeFollowed = userBeFollowed.getFollowers();
        currentFollowersOfUserBeFollowed.add(userStartFollowing.getId());
        userBeFollowed.setFollowers(currentFollowersOfUserBeFollowed);

        userRepositoryImp.saveUser(userStartFollowing);
        userRepositoryImp.saveUser(userBeFollowed);

        return userStartFollowing;
    }

    public boolean removeFollower(User user, User follower) {
        removeFollower(user, follower.getId());
        removeFollowing(follower, user.getId());
        userRepositoryImp.saveUser(follower);
        return userRepositoryImp.saveUser(user);
    }

    public boolean removeFollowing(User user, User following) {
        removeFollowing(user, following.getId());
        removeFollower(following, user.getId());
        userRepositoryImp.saveUser(following);
        return userRepositoryImp.saveUser(user);
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

    public User getUserById(Long id) {
        return userRepositoryImp.getUserById(id);
    }

    public List<User> filterUserByFullName(String fullName) {
        List<User> allUsers = findAllUsers();
        List<User> userAfterFiltering = new ArrayList<>();
        if (!allUsers.isEmpty()) {
            userAfterFiltering = allUsers.stream()
                    .filter(user -> user.getFullName().toLowerCase().contains(fullName.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return userAfterFiltering;
    }

    public List<User> findAllUsers() {
        return userRepositoryImp.getAllUsers();
    }

    public List<User> deleteUser(User user) {
        userRepositoryImp.deleteUser(user.getId());
        if (userRepositoryImp.getUserById(user.getId()) != null) {
            throw new UnSuccessDeleteException("User with - " + user.getId() + " id was not deleted");
        }
        return findAllUsers();
    }
}
