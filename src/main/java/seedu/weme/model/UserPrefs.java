package seedu.weme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.weme.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path wemeFilePath = Paths.get("data" , "weme.json");
    private Path likeDataFilePath = Paths.get("data" , "likeData.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setMemeBookFilePath(newUserPrefs.getMemeBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getMemeBookFilePath() {
        return wemeFilePath;
    }

    public void setMemeBookFilePath(Path memeBookFilePath) {
        requireNonNull(memeBookFilePath);
        this.wemeFilePath = memeBookFilePath;
    }

    public Path getLikeDataFilePath() {
        return likeDataFilePath;
    }

    public void setLikeDataFilePath(Path likeDataFilePath) {
        requireNonNull(likeDataFilePath);
        this.likeDataFilePath = likeDataFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && wemeFilePath.equals(o.wemeFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, wemeFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + wemeFilePath);
        return sb.toString();
    }

}