package seedu.weme.logic.commands.exportcommand;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;

/**
 * Exports memes from staging area to a specified path.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String EXPORT_FOLDER_NAME = "export";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": exports memes in the export context to a given directory.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: "
            + PREFIX_FILEPATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:/Users/username/Downloads/meme_folder/ ";
    public static final String MESSAGE_SUCCESS = "Memes exported successfully!";
    public static final String MESSAGE_INVALID_DEFAULT_EXPORT_PATH =
            "Invalid Default Export Path set. "
                    + "Configure it in preferences.json and restart the application";

    private final DirectoryPath exportPath;
    private boolean isApplicationPath;

    /**
     * Creates an ExportCommand to export the memes from the specified {@code Path}.
     */
    public ExportCommand(DirectoryPath path) {
        requireNonNull(path);
        exportPath = path;
    }

    /**
     * Creates an ExportCommand to export memes into the default directory path.
     */
    public ExportCommand(boolean isApplicationPath) {
        exportPath = null;
        this.isApplicationPath = isApplicationPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Path> pathList = model.getExportPathList();
            FileUtil.copyFiles(pathList, getExportPath(model));
            model.clearExportList();
        } catch (IOException ioe) {
            throw new CommandException(ioe.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Path getExportPath(Model model) throws CommandException, IOException {
        if (isNull(exportPath)) {
            assert nonNull(isApplicationPath) : "isApplicationExportPath is null";
            if (isApplicationPath) {
                return FileUtil.getApplicationFolderPath(EXPORT_FOLDER_NAME);
            } else {
                Path defaultExportPath = model.getDefaultExportPath();
                if (FileUtil.isValidDirectoryPath(defaultExportPath.toString())) {
                    return defaultExportPath;
                } else {
                    throw new CommandException(MESSAGE_INVALID_DEFAULT_EXPORT_PATH);
                }
            }
        }
        return exportPath.toPath();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && exportPath.equals(((ExportCommand) other).exportPath));
    }

}
