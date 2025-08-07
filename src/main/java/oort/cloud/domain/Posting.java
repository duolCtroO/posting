package oort.cloud.domain;

import oort.cloud.Position;

import java.util.Objects;

public class Posting {
    private Long id;
    private String senderId;
    private String content;
    private Position position;

    private Posting() {}

    public static Posting create(Long id, String senderId, String content, Position position) {
        Posting posting = new Posting();
        posting.id        = Objects.requireNonNull(id, "required id");
        posting.senderId  = Objects.requireNonNull(senderId, "required senderId");
        posting.content   = Objects.requireNonNull(content, "required content");
        posting.position = Objects.requireNonNull(position, "required position");

        return posting;
    }

    public Position getPosition() {
        return position;
    }
}
