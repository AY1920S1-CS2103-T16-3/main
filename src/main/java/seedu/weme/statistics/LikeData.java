package seedu.weme.statistics;

import seedu.weme.model.meme.Meme;

/**
 * An interface for Like/Dislike feature.
 */
public interface LikeData {

    /**
     * Returns the like data about a meme.
     */
    int getLikesByMeme(Meme meme);

    /**
     * Like a meme once.
     */
    void incrementLikesByMeme(Meme meme);

    /**
     * Dislike a meme once.
     */
    void decrementLikesByMeme(Meme meme);

    /**
     * Delete like data for a meme.
     */
    void deleteLikesByMeme(Meme meme);
}