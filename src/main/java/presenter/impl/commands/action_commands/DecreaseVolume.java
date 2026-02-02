package presenter.impl.commands.action_commands;

import presenter.impl.DefaultPresenter;

import java.util.List;

public class DecreaseVolume extends Command {
    public DecreaseVolume(DefaultPresenter presenter) {
        super(presenter);
    }

    @Override
    public void execute(String contextString) throws Exception {
        float volume = defaultPresenter.getVolume();
        if (volume - 2.5f >= -35.f) {
            defaultPresenter.setVolume(volume - 2.5f);
        } else {
            defaultPresenter.setVolume(-1000.f);
        }
    }
}
