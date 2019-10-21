package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.LikeCommand;
import seedu.weme.logic.commands.MemeDeleteCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.statistics.LikeData;

/**
 * Parses input arguments and creates a new MemeDeleteCommand object
 */
public class MemeLikeCommandParser implements Parser<LikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LikeCommand
     * and returns a LikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LikeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LikeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LikeCommand.MESSAGE_USAGE), pe);
        }
    }

}
