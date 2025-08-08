package oort.cloud.repository;

import oort.cloud.Position;
import oort.cloud.domain.Posting;
import oort.cloud.query.PostingQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InMemoryPostRepository implements PostingRepository{
    private final List<Posting> postings = new ArrayList<>();
    private Position currentPosition = Position.INITIAL_POSITION;

    @Override
    public Posting save(Long id, String userId, String content) {
        currentPosition = currentPosition.next();

        Posting posting = Posting.create(id, userId, content, currentPosition);

        postings.add(posting);

        return posting;
    }

    @Override
    public void delete(Posting posting) {
        postings.remove(posting);
    }

    @Override
    public void update(Posting posting) {

    }

    @Override
    public void query(PostingQuery postingQuery, Consumer<Posting> callback) {
        if(!postingQuery.hasUsers()){
            return;
        }

        Set<String> users = postingQuery.getUsers();
        Position lastseenPosition = postingQuery.getLastseenPosition();

        postings.stream()
                .filter(posting -> users.contains(posting.getSenderId()))
                .filter(posting -> posting.isAfter(lastseenPosition))
                .forEach(callback);
    }
}
