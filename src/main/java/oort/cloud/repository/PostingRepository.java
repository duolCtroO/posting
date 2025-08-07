package oort.cloud.repository;

import oort.cloud.domain.Posting;
import oort.cloud.query.PostingQuery;

import java.util.function.Consumer;

public interface PostingRepository {
    void save();
    void delete();
    void update();
    void query(PostingQuery postingQuery, Consumer<Posting> callback);
}
