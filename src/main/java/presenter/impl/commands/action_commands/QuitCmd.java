package presenter.impl.commands.action_commands;

import presenter.impl.DefaultPresenter;

import java.util.List;

public class QuitCmd extends Command {
    public QuitCmd(DefaultPresenter defaultPresenter) {
        super(defaultPresenter);
    }

    @Override
    public void execute(String contextString) throws Exception {
        defaultPresenter.shutdown();
    }
}
