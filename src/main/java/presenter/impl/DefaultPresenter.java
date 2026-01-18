package presenter.impl;

import presenter.IPresenter;
import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.VisualWidgetDTO;
import view.Action;
import view.IView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultPresenter implements IPresenter {
    // Fields:

    private boolean cycleState = true;

    private IView view;
    private List<Widget> buttons;
    private List<Widget> labels;

    private final CommandLibrary commandLibrary = new CommandLibrary();

    // Private DTO methods:

    private static VisualWidgetDTO transferToVisualWidgetDTO(Widget widget) {
        return new VisualWidgetDTO(
            widget.getShape(),
            widget.getShapeColor(),
            widget.getText(),
            widget.getTextColor(),
            widget.getNormalizedPosition()
        );
    }

    private List<VisualWidgetDTO> prepareVisualDTO() {
        List<VisualWidgetDTO> DTOs = new ArrayList<>();
        for (Widget widget : labels) {
            if (!widget.isActive()) continue;
            DTOs.add(transferToVisualWidgetDTO(widget));
        }

        for (Widget widget : buttons) {
            if (!widget.isActive()) continue;
            DTOs.add(transferToVisualWidgetDTO(widget));
        }

        return DTOs;
    }

    private static ActionWidgetDTO transferToActionWidgetDTO(Widget widget) {
        return new ActionWidgetDTO(
            widget.getId(),
            widget.getShape(),
            widget.getNormalizedPosition()
        );
    }

    private List<ActionWidgetDTO> prepareActionDTO() {
        List<ActionWidgetDTO> DTOs = new ArrayList<>();

        for (Widget widget : buttons) {
            if (!widget.isActive()) continue;
            DTOs.add(transferToActionWidgetDTO(widget));
        }

        return DTOs;
    }

    private void shutdown() {
        this.cycleState = false;
    }

    private void loadCommands() {
        commandLibrary.registerCommand("EXIT", List.of(
            this::shutdown
        ));
    }

    private void handleButtonClick(int id) {
        for (Widget button : buttons) {
            if (button.getId() == id) {
                button.executeClick();
                break;
            }
        }
    }

    private void processActions(List<Action> actions) {
        for  (Action action : actions) {
            System.out.println("Working with action: " + action);
            switch (action) {
                case Action.WidgetClicked(int id) -> {
                    handleButtonClick(id);
                }
                case Action.Quit() -> {
                    System.out.println("Shutting down...");
                    view.close();
                    shutdown();
                }
            }
        }
    }

    // Public methods:

    public DefaultPresenter(IView view, List<Widget> buttons, List<Widget> labels) {
        this.view = view;
        this.buttons = buttons != null ? buttons : new ArrayList<>();
        this.labels = labels != null ? labels : new ArrayList<>();

        this.loadCommands();
    }

    @Override
    public boolean run(double deltaTime) {
        UserInterfaceDTO uiDTO = new UserInterfaceDTO(prepareVisualDTO());

        view.renderUI(uiDTO);

        processActions(view.getActions(prepareActionDTO()));

        view.update();

        return cycleState;
    }
}
