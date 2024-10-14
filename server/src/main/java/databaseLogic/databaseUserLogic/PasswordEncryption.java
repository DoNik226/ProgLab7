package databaseLogic.databaseUserLogic;

import java.io.InputStream;
import java.io.InputStreamReader;

public interface PasswordEncryption {
    static char[] getPepper() {
        try (InputStream is = new ResourceStreamLogic("/pepper.txt").getResourceStream()) {
            assert is != null;
            InputStreamReader reader = new InputStreamReader(is);
            char[] pepper = new char[93];
            var ignored = reader.read(pepper, 0, 93);
            return pepper;
        } catch (Exception e) {
            System.out.println("Cannot get pepper =( " + e);
            System.exit(-47);
        }
        return new char[0];
    }

    String encrypt(char[] sequence);

    char[] generateSalt();
}
