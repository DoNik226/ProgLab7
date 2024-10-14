package fileLogic;

import java.io.IOException;
import java.util.*;

/**
 * Base Reader interface. Should be implemented for using in Loader class.
 *
 * @see Loader
 * @since 1.0
 * @author Nikita
 */
public interface BaseReader {

    /**
     * Base method for reading file and providing Address-Value interpretation of this File.
     *
     * @param path Full path to file.
     * @return Collection of values with full address from File.
     * @throws IOException When something will go wrong during file handling
     */
    LinkedHashMap<String[], String> readFromFile(String path) throws IOException;
}
