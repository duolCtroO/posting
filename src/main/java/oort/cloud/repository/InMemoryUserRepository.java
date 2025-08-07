package oort.cloud.repository;

import oort.cloud.domain.User;
import oort.cloud.status.FollowStatus;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> userStore = new HashMap<>();
    private Map<User, List<User>> followerStore = new HashMap<>();

    @Override
    public void save(User user) {
        User newUser = Objects.requireNonNull(user, "required user");

        if(userStore.containsKey(newUser.getUserId())){
            throw new IllegalArgumentException("user already exists");
        }

        userStore.put(newUser.getUserId(), newUser);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.of(userStore.get(userId));
    }

    @Override
    public FollowStatus follow(User follow, User userToFollow) {
        if(userStore.get(userToFollow.getUserId()) == null)
            return FollowStatus.INVALID_USER;

        followerStore
                .computeIfAbsent(follow, k -> new ArrayList<>())
                .add(userToFollow);

        return FollowStatus.SUCCESS;
    }
}
