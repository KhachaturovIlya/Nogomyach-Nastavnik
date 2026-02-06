package presenter.impl.commands.context_commands;

import presenter.IContextCommand;
import presenter.impl.DefaultPresenter;

public abstract class ContextCommand implements IContextCommand {
    DefaultPresenter defaultPresenter;

    public ContextCommand(DefaultPresenter defaultPresenter) {
        this.defaultPresenter = defaultPresenter;
    }
}
