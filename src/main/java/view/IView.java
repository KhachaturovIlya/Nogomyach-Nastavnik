package view;

import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.WorldDTO;

import java.util.List;

public interface IView {
    void renderWorld(WorldDTO worldDTO);

    void renderUI(UserInterfaceDTO userInterfaceDTO);

    void setVolume(float volume);

    List<Action> getActions(List<ActionWidgetDTO> actionWidgetDTOs);

    void update();

    void close();
}