package presenter.impl.commands.context_commands;

import presenter.impl.DefaultPresenter;

import java.util.ArrayList;
import java.util.List;

public class VolumeLevel extends ContextCommand {
    public VolumeLevel(DefaultPresenter defaultPresenter) {
        super(defaultPresenter);
    }

    @Override
    public List<String> execute(String context) throws Exception {
        List<String> result = new ArrayList<>();

        int size = (int) ((defaultPresenter.getVolume() + 35.f) / 2.5);
        while (--size >= 0) {
            result.add("");
        }

        return result;
    }
}
