package oort.cloud.domain;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class User {
    private String userId;
    private String password;

    private User(){}

    public static User create(String userId, String password){
        User user = new User();
        user.userId = Objects.requireNonNull(userId, "required userId");

        String plainPassword = Objects.requireNonNull(password, "required password");
        user.password = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
