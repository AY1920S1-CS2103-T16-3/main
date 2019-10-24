package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_MEMES;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.StatsManager;
import seedu.weme.testutil.WemeBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Weme(), new Weme(modelManager.getWeme()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDataFilePath(Paths.get("weme/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDataFilePath(Paths.get("new/weme/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setWemeFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDataFilePath(null));
    }

    @Test
    public void setWemeFilePath_validPath_setsWemeFilePath() {
        Path path = Paths.get("weme/book/file/path");
        modelManager.setDataFilePath(path);
        assertEquals(path, modelManager.getDataFilePath());
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInWeme_returnsFalse() {
        assertFalse(modelManager.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeInWeme_returnsTrue() {
        modelManager.addMeme(DOGE);
        assertTrue(modelManager.hasMeme(DOGE));
    }

    @Test
    public void getFilteredMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMemeList().remove(0));
    }

    @Test
    public void equals() {
        Weme weme = new WemeBuilder().withMeme(DOGE).build();
        Weme differentWeme = new Weme();
        UserPrefs userPrefs = new UserPrefs();
        Stats stats = new StatsManager();

        // same values -> returns true
        modelManager = new ModelManager(weme, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(weme, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different weme -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentWeme, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);

        // different filteredList -> returns false
        String[] keywords = JOKER.getFilePath().toString().split("\\s+");
        modelManager.updateFilteredMemeList(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(weme, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDataFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(weme, differentUserPrefs)));
    }
}
