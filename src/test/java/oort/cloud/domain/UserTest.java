package oort.cloud.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create() {
        User user = User.create("userId", "password");
        assertNotNull(user);
    }

    @Test
    void validate_password() {
        User user = User.create("userId", "password");
        String savedPassword = user.getPassword();

        assertTrue(BCrypt.checkpw("password", savedPassword));
        assertFalse(BCrypt.checkpw("wrongPassword", savedPassword));
    }

}