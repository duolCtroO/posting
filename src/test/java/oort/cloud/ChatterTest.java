package oort.cloud;

import oort.cloud.repository.InMemoryPostRepository;
import oort.cloud.repository.InMemoryUserRepository;
import oort.cloud.repository.PostingRepository;
import oort.cloud.repository.UserRepository;
import oort.cloud.status.FollowStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChatterTest {
    UserRepository userRepository = new InMemoryUserRepository();
    PostingRepository postingRepository = new InMemoryPostRepository();

    private Chatter chatter = new Chatter(userRepository, postingRepository);

    @Mock
    ReceiverEndPoint receiverEndPoint;

    @Test
    void integration(){
        chatter.registerUser("userId1", "password");
        chatter.registerUser("userId2", "password");

        Optional<SenderEndPoint> userId1 = chatter.login("userId1", "password", receiverEndPoint);
        Optional<SenderEndPoint> userId2 = chatter.login("userId2", "password", receiverEndPoint);

        Assertions.assertTrue(userId1.isPresent());
        Assertions.assertTrue(userId2.isPresent());

        FollowStatus userId2FollowStatus = userId1.get().follow("userId2");
        FollowStatus userId1FollowStatus = userId2.get().follow("userId1");

        Assertions.assertEquals(FollowStatus.SUCCESS, userId2FollowStatus);
        Assertions.assertEquals(FollowStatus.SUCCESS, userId1FollowStatus);

        userId1.get().sendPosting(1L, "test1");
        userId2.get().sendPosting(2L, "test2");


    }

}