package view;

import shared.WorldDTO;

public interface IView {
    void renderWorld(WorldDTO worldDTO);

    void update();
}
