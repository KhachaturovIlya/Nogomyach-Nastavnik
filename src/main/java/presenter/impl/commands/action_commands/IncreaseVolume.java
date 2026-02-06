package presenter.impl.commands.action_commands;

import presenter.impl.DefaultPresenter;

import java.util.List;

public class IncreaseVolume extends Command {
    public IncreaseVolume(DefaultPresenter defaultPresenter) {
        super(defaultPresenter);
    }

    @Override
    public void execute(String contextString) throws Exception {
        float volume = defaultPresenter.getVolume();
        if (volume <= -35.f) {
            defaultPresenter.setVolume(-32.5f);
        } else if (volume + 2.5f <= 5.f) {
            defaultPresenter.setVolume(volume + 2.5f);
        }
    }
}
