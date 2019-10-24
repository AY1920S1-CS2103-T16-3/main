package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalMemeBook.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.statistics.StatsManager;

public class MemeClearCommandTest {

    @Test
    public void execute_emptyMemeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitMemeBook();

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMemeBook_success() {
        Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs(), new StatsManager());
        Model expectedModel = new ModelManager(getTypicalMemeBook(), new UserPrefs(), new StatsManager());
        expectedModel.setMemeBook(new MemeBook());
        expectedModel.commitMemeBook();

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}