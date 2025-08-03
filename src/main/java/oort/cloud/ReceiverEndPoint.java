package oort.cloud;

import oort.cloud.domain.Posting;

public interface ReceiverEndPoint {
    void onPosting(Posting chatt);
}
