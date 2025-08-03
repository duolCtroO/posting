package oort.cloud;

import oort.cloud.domain.User;
import oort.cloud.status.FollowStatus;

public class SenderEndPoint {
    private final User user;
    private final Posting chatter;

    public SenderEndPoint(User user, Posting chatter) {
        this.user = user;
        this.chatter = chatter;
    }

    public FollowStatus follow(final String userIdToFollow){
        return chatter.follow(user, userIdToFollow);
    }

    public Position sendPosting(final String userId, final String content){
        return null;
    }


}
