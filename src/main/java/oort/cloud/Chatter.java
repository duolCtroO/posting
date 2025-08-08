package oort.cloud;

import oort.cloud.domain.Posting;
import oort.cloud.domain.User;
import oort.cloud.query.PostingQuery;
import oort.cloud.repository.PostingRepository;
import oort.cloud.repository.UserRepository;
import oort.cloud.status.FollowStatus;

import java.util.Optional;

public class Chatter {

    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    public Chatter(UserRepository userRepository, PostingRepository postingRepository) {
        this.userRepository = userRepository;
        this.postingRepository = postingRepository;
    }

    public Optional<SenderEndPoint> login(String userId, String password, ReceiverEndPoint receiverEndPoint){
        Optional<User> authUser = userRepository.findById(userId)
                .filter(savedUser -> savedUser.authenticate(password));

        authUser.ifPresent(user -> {
            user.online(receiverEndPoint);
            postingRepository.query(new PostingQuery()
                                            .inUsers(user.getFollowings())
                                            .lastSeenPosition(user.getLastseenPosition()),
                    user::receivePosting
            );
        });

        return authUser.map(user -> new SenderEndPoint(user, this));
    }


    public void registerUser(String userId, String password){
        User user = User.create(userId, password, Position.INITIAL_POSITION);
        userRepository.save(user);
    }

    public FollowStatus follow(User follow, String followerId){
        return userRepository.findById(followerId)
                .map(userToFollow -> userRepository.follow(follow, userToFollow))
                .orElse(FollowStatus.INVALID_USER);
    }

    public Position sendPosting(Long id, User user, String content){
        Posting savedPosting = postingRepository.save(id, user.getUserId(), content);

        user.getFollowers()
                .stream()
                .filter(User::isOnline)
                .forEach(follower -> follower.receivePosting(savedPosting));

        return savedPosting.getPosition();
    }
}
