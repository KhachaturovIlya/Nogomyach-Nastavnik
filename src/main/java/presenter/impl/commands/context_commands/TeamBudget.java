package presenter.impl.commands.context_commands;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import presenter.impl.CommandLibrary;
import presenter.impl.DefaultPresenter;
import presenter.impl.commands.action_commands.Command;

import java.util.List;

public class TeamBudget extends ContextCommand {
    public TeamBudget(DefaultPresenter presenter) {
        super(presenter);
    }

    @Override
    public List<String> execute(String context) throws Exception {
        return List.of("11002300");
    }
}
