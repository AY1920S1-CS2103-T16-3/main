package seedu.weme.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class MemeViewPanel extends UiPart<Region> {

    private static final String FXML = "MemeViewPanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Weme level 4</a>
     */

    public final Meme meme;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;

    public MemeViewPanel() {
        super(FXML);
        meme = null;
    }

    public MemeViewPanel(Meme meme,
                         int displayedIndex,
                         int numOfLikes,
                         int numOfDislikes) {
        super(FXML);
        this.meme = meme;
        display.setImage(new Image(meme.getImagePath().toUrl().toString(), 200, 200, true, true, true));
        description.setText(meme.getDescription().value);
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        likes.setText(" " + numOfLikes + " ");
        dislikes.setText(" " + numOfDislikes + " ");
    }

    /**
     * Updates the card content except for the meme image.
     *
     * @param meme the meme this card is for
     */
    public void update(Meme meme, int numOfLikes, int numOfDislikes) {
        description.setText(meme.getDescription().value);
        tags.getChildren().clear();
        display.setImage(new Image(meme.getImagePath().toUrl().toString(), 500, 500, true, true, true));
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        likes.setText(" " + numOfLikes + " ");
        dislikes.setText(" " + numOfDislikes + " ");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeViewPanel)) {
            return false;
        }

        // state check
        MemeViewPanel card = (MemeViewPanel) other;
        return id.getText().equals(card.id.getText())
                && meme.equals(card.meme)
                && likes.equals(card.likes)
                && dislikes.equals(card.dislikes);
    }
}
