import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileManagerTest {
    /**
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: used to test the parseStopFile() method.
     */
    @Test
    void parseStopFile() {
        FileManager fm = new FileManager();
        File testFile = new File("TEST_FILENAME");
        //TestCase1: checks to see if parseStopFile throws correct exception type for invalid file
        assertThrows(FileNotFoundException.class, () -> fm.parseStopFile(testFile));
    }

    @Test
    void loadFromValidFiles() {
    }

    @Test
    void isValid() {

    }
}