package oort.cloud;

import oort.cloud.domain.User;
import oort.cloud.exception.ApiException;
import oort.cloud.query.PostingQuery;
import oort.cloud.repository.PostingRepository;
import oort.cloud.repository.UserRepository;
import oort.cloud.status.FollowStatus;

import java.util.Optional;

public class Posting {

    private final UserRepository userRepository;
    private final PostingRepository chatterRepository;
    private final SenderEndPoint senderEndPoint;

    public Posting(UserRepository userRepository, PostingRepository chatterRepository, SenderEndPoint senderEndPoint) {
        this.userRepository = userRepository;
        this.chatterRepository = chatterRepository;
        this.senderEndPoint = senderEndPoint;
    }

    public Optional<SenderEndPoint> login(String userId, String password, ReceiverEndPoint receiverEndPoint){
        Optional<User> authUser = userRepository.findById(userId)
                .filter(savedUser -> savedUser.getPassword().equals(password));

        authUser.ifPresent(user -> {
            user.online(receiverEndPoint);
            chatterRepository.query(new PostingQuery()
                                            .inUsers(user.getFollowings())
                                            .lastSeenPosition(user.getLastseenPosition()),
                    user::receivePosting
            );
        });

        return authUser.map(user -> new SenderEndPoint(user, this));
    }


    public void registerUser(String userId, String password){
        User user = User.create(userId, password, new Position(-1), senderEndPoint);

        userRepository.save(user);
    }

    public FollowStatus follow(User follow, String followerId){
        return userRepository.findById(followerId)
                .map(userToFollow -> userRepository.follow(follow, userToFollow))
                .orElse(FollowStatus.INVALID_USER);
    }
}
