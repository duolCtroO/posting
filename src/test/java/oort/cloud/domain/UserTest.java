package oort.cloud.domain;

import oort.cloud.Position;
import oort.cloud.SenderEndPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Mock
    private SenderEndPoint senderEndPoint;


    @Test
    void create() {
        User user = User.create("userId", "password", new Position(-1), senderEndPoint);
        assertNotNull(user);
    }

    @Test
    void validate_password() {
        User user = User.create("userId", "password", new Position(-1), senderEndPoint);
        String savedPassword = user.getPassword();

        assertTrue(BCrypt.checkpw("password", savedPassword));
        assertFalse(BCrypt.checkpw("wrongPassword", savedPassword));
    }

}