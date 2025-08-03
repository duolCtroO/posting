package oort.cloud.repository;

import oort.cloud.domain.User;
import oort.cloud.status.FollowStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {
    private final InMemoryRepository repository = new InMemoryRepository();

    @Test
    void save() {
        User user = User.create("userId", "password");
        repository.save(user);

        User savedUser = repository.findById("userId").get();

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(user.getUserId(), savedUser.getUserId());
        Assertions.assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    void duplicateSave() {
        User user1 = User.create("userId", "password");
        User user2 = User.create("userId", "password");
        repository.save(user1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> repository.save(user2));
    }

    @Test
    void success_follow() {
        User user1 = User.create("userId1", "password");
        User user2 = User.create("userId2", "password");
        repository.save(user1);
        repository.save(user2);

        FollowStatus status = repository.follow(user1, user2);

        Assertions.assertEquals(FollowStatus.SUCCESS, status);
    }

    @Test
    void fail_follow() {
        User user1 = User.create("userId1", "password");
        User user2 = User.create("userId2", "password");
        repository.save(user1);

        FollowStatus status = repository.follow(user1, user2);

        Assertions.assertEquals(FollowStatus.INVALID_USER, status);
    }

}