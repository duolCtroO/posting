package oort.cloud;

import oort.cloud.domain.User;
import oort.cloud.status.FollowStatus;

import java.util.Objects;

public class SenderEndPoint {
    private final User user;
    private final Chatter chatter;

    public SenderEndPoint(User user, Chatter chatter) {
        this.user = user;
        this.chatter = chatter;
    }

    public FollowStatus follow(final String userIdToFollow){
        return chatter.follow(user, userIdToFollow);
    }

    public Position sendPosting(final Long id, final String content){
        Objects.requireNonNull(content, "content must not be null");

        return chatter.sendPosting(id, user, content);
    }

    public User getUser() {
        return user;
    }
}
