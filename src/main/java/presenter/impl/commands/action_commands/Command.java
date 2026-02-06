package presenter.impl.commands.action_commands;

import presenter.ICommand;
import presenter.impl.DefaultPresenter;

public abstract class Command implements ICommand {
    DefaultPresenter defaultPresenter;
    public Command(DefaultPresenter defaultPresenter) {
        this.defaultPresenter = defaultPresenter;
    }

}
