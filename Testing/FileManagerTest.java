import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(FileNotFoundException.class, () -> {
            fm.parseStopFile(testFile);
        });
    }

    @Test
    void loadFromValidFiles() {
    }

    /**
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: used to test the addFile() method.
     */
    @Test
    void addFile() throws Exception {
        FileManager fm = new FileManager();
        //TestCase1: checks to see if addFile returns false if file is not valid.
        assertFalse(fm.addFile(new File("notValidFile")));
    }

    @Test
    void isValid(){

    }
}