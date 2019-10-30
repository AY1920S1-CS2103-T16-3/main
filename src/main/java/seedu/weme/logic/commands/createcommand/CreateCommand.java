package seedu.weme.logic.commands.createcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.util.ImageUtil;

/**
 * Finishes meme creation and generates a meme.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_SUCCESS = "A new meme has been created and added to the collection: %s";
    private static final String MESSAGE_IO_ERROR = "An error occurred while writing the generated meme image to disk";

    private final Description description;
    private final Set<Tag> tags;

    public CreateCommand(Description description, Set<Tag> tags) {
        requireAllNonNull(description, tags);
        this.description = description;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MemeCreation memeCreation = model.getMemeCreation();
        ReadOnlyUserPrefs userPrefs = model.getUserPrefs();
        Path newPath = ImageUtil.getNewImagePath(userPrefs.getMemeImagePath(), "jpg");
        try {
            memeCreation.generate(newPath);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_IO_ERROR, ioe);
        }
        Meme newMeme = new Meme(new ImagePath(newPath.toString()), description, tags);
        model.addMeme(newMeme);
        model.setContext(ModelContext.CONTEXT_TEMPLATES);
        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, newMeme.toString()));
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }

}
