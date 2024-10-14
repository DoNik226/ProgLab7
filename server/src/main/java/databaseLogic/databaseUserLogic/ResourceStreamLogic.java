package databaseLogic.databaseUserLogic;

import java.io.InputStream;

public class ResourceStreamLogic {
    private final InputStream resourceStream;

    public ResourceStreamLogic(String fileName) {
        resourceStream = this.getClass().getResourceAsStream(fileName);
    }

    public InputStream getResourceStream() {
        return resourceStream;
    }
}
