package oort.cloud.domain;

import java.util.Objects;

public class Posting {
    private Long id;
    private String senderId;
    private String content;

    private Posting() {}

    public static Posting create(Long id, String senderId, String content){
        Posting posting = new Posting();
        posting.id        = Objects.requireNonNull(id, "required id");
        posting.senderId  = Objects.requireNonNull(senderId, "required senderId");
        posting.content   = Objects.requireNonNull(content, "required content");

        return posting;
    }




}
