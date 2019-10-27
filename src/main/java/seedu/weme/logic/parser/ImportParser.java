package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.HelpCommand;
import seedu.weme.logic.commands.ImportCommand;
import seedu.weme.logic.commands.LoadCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses user input in the import context.
 */
public class ImportParser extends WemeParser {

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommand();

        default:
            return super.parseCommand(userInput);
        }
    }
}