package oort.cloud.query;

import oort.cloud.Position;
import oort.cloud.domain.Posting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PostingQuery {
    private Set<String> users;
    private Position lastseenPosition;

    public Position getLastseenPosition() {
        return lastseenPosition;
    }

    public Set<String> getUsers() {
        return users;
    }

    public PostingQuery inUsers(Set<String> users) {
        this.users = users;
        return this;
    }

    public PostingQuery inUsers(String... user) {
        users = new HashSet<>(Arrays.asList(user));
        return this;
    }

    public PostingQuery lastSeenPosition(Position lastseenPosition) {
        this.lastseenPosition = lastseenPosition;
        return this;
    }

}
