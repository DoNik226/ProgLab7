package databaseLogic.databaseUserLogic;

import com.google.common.hash.Hashing;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionImplSHA512 implements PasswordEncryption {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String encrypt(char[] sequence) {
        return Hashing.sha512().hashBytes(StandardCharsets.UTF_8.encode(CharBuffer.wrap(sequence)).array()).toString();
    }

    @Override
    public char[] generateSalt() {
        byte[] salt = new byte[160];
        RANDOM.nextBytes(salt);
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(
                Base64.getEncoder().encode(salt))).array();
    }
}
