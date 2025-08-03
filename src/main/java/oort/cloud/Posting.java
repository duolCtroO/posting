package oort.cloud;

import oort.cloud.domain.User;
import oort.cloud.exception.ApiException;
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
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("등록된 유저 정보와 다릅니다"));

        if(!password.equals(savedUser.getPassword())){
            throw new ApiException("등록된 유저 정보와 다릅니다");
        }

        return Optional.of(senderEndPoint);
    }


    public void registerUser(String userId, String password){
        User user = User.create(userId, password);

        userRepository.save(user);
    }

    public FollowStatus follow(User follow, String followerId){
        return userRepository.findById(followerId)
                .map(userToFollow -> userRepository.follow(follow, userToFollow))
                .orElse(FollowStatus.INVALID_USER);
    }
}
