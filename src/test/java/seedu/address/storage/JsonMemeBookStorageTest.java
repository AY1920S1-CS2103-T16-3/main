package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMemes.ALICE;
import static seedu.address.testutil.TypicalMemes.HOON;
import static seedu.address.testutil.TypicalMemes.IDA;
import static seedu.address.testutil.TypicalMemes.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.MemeBook;
import seedu.address.model.ReadOnlyMemeBook;

public class JsonMemeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMemeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyMemeBook> readAddressBook(String filePath) throws Exception {
        return new JsonMemeBookStorage(Paths.get(filePath)).readMemeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatMemeBook.json"));
    }

    @Test
    public void readAddressBook_invalidMemeAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidMemeMemeBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidMemeAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidMemeMemeBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        MemeBook original = getTypicalAddressBook();
        JsonMemeBookStorage jsonAddressBookStorage = new JsonMemeBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveMemeBook(original, filePath);
        ReadOnlyMemeBook readBack = jsonAddressBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeme(HOON);
        original.removeMeme(ALICE);
        jsonAddressBookStorage.saveMemeBook(original, filePath);
        readBack = jsonAddressBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Save and read without specifying file path
        original.addMeme(IDA);
        jsonAddressBookStorage.saveMemeBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readMemeBook().get(); // file path not specified
        assertEquals(original, new MemeBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyMemeBook addressBook, String filePath) {
        try {
            new JsonMemeBookStorage(Paths.get(filePath))
                    .saveMemeBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new MemeBook(), null));
    }
}
