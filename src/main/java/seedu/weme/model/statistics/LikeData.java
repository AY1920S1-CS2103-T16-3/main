package seedu.weme.model.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * The like data storage in Stats.
 */
public class LikeData {

    private final ObservableMap<String, SimpleIntegerProperty> likeMap =
            FXCollections.observableHashMap();
    private final ObservableMap<String, SimpleIntegerProperty> unmodifiableLikeMap =
            FXCollections.unmodifiableObservableMap(likeMap);

    /**
     * Constructs an empty LikeData
     */
    public LikeData() {}

    /**
     * Constructs a LikeData filled with the provided likeMap
     */
    public LikeData(ObservableMap<String, SimpleIntegerProperty> likeMap) {
        setLikeMap(likeMap);
    }

    /**
     * Sets the current set of {@code LikeData} with a replacement.
     */
    public void setLikeMap(Map<String, SimpleIntegerProperty> replacement) {
        requireAllNonNull(replacement);
        likeMap.clear();
        likeMap.putAll(replacement);
    }

    /**
     * Sets like count of a meme.
     */
    public void setLikesByMemeRef(String memeRef, int change) {
        SimpleIntegerProperty currLikes = likeMap.getOrDefault(memeRef, new SimpleIntegerProperty(0));
        currLikes.set(currLikes.get() + change);
        if (!likeMap.containsKey(memeRef)) {
            likeMap.put(memeRef, currLikes);
        }
    }

    /**
     * Returns the like count of a meme by its URL.
     */
    public int getLikesByMemeRef(String memeRef) {
        return likeMap.getOrDefault(memeRef, new SimpleIntegerProperty(Integer.MAX_VALUE)).get();
    }

    /**
     * Returns an unmodifiable view of LikeData.
     */
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return unmodifiableLikeMap;
    }

    /**
     * Deletes like count of a meme by its URL.
     * @param memeRef
     */
    public void deleteLikesByMemeRef(String memeRef) {
        likeMap.remove(memeRef);
    }

}
