package seedu.weme.ui;

import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;


/**
 * Panel containing the list of memes.
 */
public class MemeGridPanel extends UiPart<Region> {
    private static final String FXML = "MemeGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemeGridPanel.class);

    @FXML
    private GridView<Meme> memeGridView;

    private ObservableMap<String, SimpleIntegerProperty> likeData;
    private ObservableMap<String, SimpleIntegerProperty> dislikeData;

    public MemeGridPanel(ObservableList<Meme> memeList,
                         ObservableMap<String, SimpleIntegerProperty> likeData,
                         ObservableMap<String, SimpleIntegerProperty> dislikeData) {
        super(FXML);
        memeGridView.setItems(memeList);
        memeGridView.setCellFactory(listView -> new MemeGridViewCell());
        this.likeData = likeData;
        this.dislikeData = dislikeData;
    }

    /**
     * Custom {@code GridCell} that displays the graphics of a {@code Meme} using a {@code MemeCard}.
     */
    class MemeGridViewCell extends GridCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);
            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                String filePath = meme.getImagePath().toString();
                SimpleIntegerProperty likes = likeData.get(filePath);
                SimpleIntegerProperty dislikes = dislikeData.get(filePath);
                setGraphic(new MemeCard(meme, getIndex() + 1, likes, dislikes).getRoot());
            }
        }
    }

}
