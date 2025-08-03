package oort.cloud.repository;

import oort.cloud.domain.User;
import oort.cloud.status.FollowStatus;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(String userId);

    FollowStatus follow(User follow, User userToFollow);
}
