package oort.cloud.repository;

import oort.cloud.domain.Posting;
import oort.cloud.query.PostingQuery;

import java.util.function.Consumer;

public interface PostingRepository {
    Posting save(Long id, String userId, String content);
    void delete(Posting posting);
    void update(Posting posting);
    void query(PostingQuery postingQuery, Consumer<Posting> callback);
}
