package view;

import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.WorldDTO;

import java.util.List;

public interface IView {
    void renderWorld(WorldDTO worldDTO);

    void renderUI(UserInterfaceDTO userInterfaceDTO);

    List<Action> getActions();

    void update(List<ActionWidgetDTO> actionWidgetDTOs);
}