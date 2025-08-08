package oort.cloud.domain;

import oort.cloud.Position;
import oort.cloud.ReceiverEndPoint;
import oort.cloud.SenderEndPoint;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.Set;

public class User {
    private String userId;
    private String password;
    private boolean isOnline = false;

    private Set<User> followers;
    private Set<String> followings;

    private Position lastseenPosition;
    private ReceiverEndPoint receiverEndPoint;

    private User(){}

    public static User create(String userId, String password, Position lastseenPosition){
        User user = new User();
        user.userId = Objects.requireNonNull(userId, "required userId");

        String plainPassword = Objects.requireNonNull(password, "required password");
        user.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        user.lastseenPosition = Objects.requireNonNull(lastseenPosition, "required position");

        return user;
    }

    public boolean authenticate(String password){
        return BCrypt.checkpw(password, this.password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void online(ReceiverEndPoint receiverEndPoint) {
        this.isOnline = true;
        this.receiverEndPoint = receiverEndPoint;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public Position getLastseenPosition() {
        return lastseenPosition;
    }

    public boolean receivePosting(final Posting posting){
        if(isOnline){
            receiverEndPoint.onPosting(posting);
            lastseenPosition = posting.getPosition();
            return true;
        }
        return false;
    }
}
