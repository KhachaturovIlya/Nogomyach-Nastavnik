package presenter.impl;

import presenter.IPresenter;
import presenter.impl.interfaces.IWidgetFileFactory;
import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.Vector2;
import shared.VisualWidgetDTO;
import view.Action;
import view.IView;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPresenter implements IPresenter {
    // Fields:

    private boolean cycleState = true;

    private final IView view;
    private final Map<Integer, Widget> buttons;
    private final Map<Integer, Widget> labels;

    private final CommandLibrary commandLibrary = new CommandLibrary();

    // Private DTO methods:

    private static Widget compressWidgetByParent(Widget widget, Widget parentWidget) {
        return new Widget(
            widget.getId(),
            widget.isActive(),
            widget.isButton(),
            widget.getShape().compressedCopy(parentWidget.getShape().getWidth(), parentWidget.getShape().getHeight()),
            widget.getShapeColor(),
            widget.getText(),
            widget.getTextColor(),
            new Vector2(parentWidget.getNormalizedPosition().x + widget.getNormalizedPosition().x * parentWidget.getShape().getWidth(),
                parentWidget.getNormalizedPosition().y + widget.getNormalizedPosition().y * parentWidget.getShape().getHeight()),

            widget.getChildrenLabels(),
            widget.getChildrenButtons(),

            widget.getClickActions()
        );
    }

    private static VisualWidgetDTO transferToVisualWidgetDTO(Widget widget) {
        return new VisualWidgetDTO(
            widget.getShape(),
            widget.getShapeColor(),
            widget.getText(),
            widget.getTextColor(),
            widget.getNormalizedPosition(),
            widget.isButton()
        );
    }

    private void prepareVisualDTORecursive(Widget widget, List<VisualWidgetDTO> visualWidgetDTOs) {
        for (Widget childrenLabel : widget.getChildrenLabels().values()) {
            Widget compressWidget = compressWidgetByParent(childrenLabel, widget);
            visualWidgetDTOs.add(transferToVisualWidgetDTO(compressWidget));
            if (!childrenLabel.getChildrenLabels().isEmpty() || !childrenLabel.getChildrenButtons().isEmpty()) {
                prepareVisualDTORecursive(compressWidget, visualWidgetDTOs);
            }
        }
        for (Widget childrenButton : widget.getChildrenButtons().values()) {
            visualWidgetDTOs.add(transferToVisualWidgetDTO(compressWidgetByParent(childrenButton, widget)));
        }
    }

    private List<VisualWidgetDTO> prepareVisualDTO() {
        List<VisualWidgetDTO> DTOs = new ArrayList<>();
        for (Widget widget : labels.values()) {
            if (!widget.isActive()) continue;
            DTOs.add(transferToVisualWidgetDTO(widget));
            if (!widget.getChildrenLabels().isEmpty() || !widget.getChildrenButtons().isEmpty()) {
                prepareVisualDTORecursive(widget, DTOs);
            }
        }

        for (Widget widget : buttons.values()) {
            if (!widget.isActive()) continue;
            DTOs.add(transferToVisualWidgetDTO(widget));
        }

        return DTOs;
    }

    private static ActionWidgetDTO transferToActionWidgetDTO(List<Integer> ids, Widget widget) {
        List<Integer> recursiveID = new ArrayList<>(ids);
        recursiveID.add(widget.getId());
        return new ActionWidgetDTO(
            recursiveID,
            widget.getShape(),
            widget.getNormalizedPosition()
        );
    }

    private void prepareActionDTORecursive(List<Integer> ids, Widget widget, List<ActionWidgetDTO> actionWidgetDTOs) {
        for (Widget childrenLabel : widget.getChildrenLabels().values()) {
            if (!childrenLabel.getChildrenLabels().isEmpty() || !childrenLabel.getChildrenButtons().isEmpty()) {
                List<Integer> recursiveID = new ArrayList<>(ids);
                recursiveID.add(childrenLabel.getId());
                Widget compressWidget = compressWidgetByParent(childrenLabel, widget);
                prepareActionDTORecursive(recursiveID, compressWidget, actionWidgetDTOs);
            }
        }
        for (Widget childrenButton : widget.getChildrenButtons().values()) {
            actionWidgetDTOs.add(transferToActionWidgetDTO(ids, compressWidgetByParent(childrenButton, widget)));
        }
    }

    private List<ActionWidgetDTO> prepareActionDTO() {
        List<ActionWidgetDTO> DTOs = new ArrayList<>();

        for (Widget widget : buttons.values()) {
            if (!widget.isActive()) continue;
            List<Integer> ids = new ArrayList<>();
            ids.add(widget.getId());
            DTOs.add(transferToActionWidgetDTO(ids, widget));
        }

        for (Widget widget : labels.values()) {
            if (!widget.isActive()) continue;
            List<Integer> ids = new ArrayList<>();
            ids.add(widget.getId());
            prepareActionDTORecursive(ids, widget, DTOs);
        }

        return DTOs;
    }

    private void shutdown() {
        System.out.println("Shutting down...");

        try {
            Thread.sleep(130);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        view.close();
        this.cycleState = false;
    }

    private void loadCommands() {
        commandLibrary.registerCommand("EXIT", List.of(
            this::shutdown
        ));
    }

    private void handleButtonClick(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;

        Widget current;
        Integer rootId = ids.getFirst();
        current = buttons.getOrDefault(rootId, labels.get(rootId));

        for (Integer id : ids) {
            if (id.equals(rootId)) continue;

            if (current == null) return;

            current = current.getChildrenButtons().getOrDefault(id,
                current.getChildrenLabels().get(id));
        }

        if (current != null) current.executeClick();
    }

    private void processActions(List<Action> actions) {
        for  (Action action : actions) {
            System.out.println("Working with action: " + action);
            switch (action) {
                case Action.WidgetClicked(List<Integer> ids) -> {
                    handleButtonClick(ids);
                }
                case Action.Quit() -> {
                    shutdown();
                }
            }
        }
    }

    // Public methods:

    public DefaultPresenter(IView view, Map<Integer, Widget> buttons, Map<Integer, Widget> labels) {
        this.view = view;
        this.buttons = buttons != null ? buttons : new HashMap<>();
        this.labels = labels != null ? labels : new HashMap<>();

        this.loadCommands();
    }

    public DefaultPresenter(IView view, IWidgetFileFactory widgetFileFactory) throws IOException {
        this.view = view;
        this.loadCommands();

        this.buttons = new HashMap<>(); //widgetFileFactory.constructButtons();
        this.labels = widgetFileFactory.constructLabels(
            Path.of("configs/scenes/footballField/widgets/labels.json"),
            commandLibrary
        );
    }

    @Override
    public boolean run(double deltaTime) {
        UserInterfaceDTO uiDTO = new UserInterfaceDTO(prepareVisualDTO());

        view.renderUI(uiDTO);

        processActions(view.getActions(prepareActionDTO()));
        for (List<Integer> pressedButtonsIds : view.getInteractedWidgets()) {
            System.out.println("Pressed buttons: " + pressedButtonsIds);
            handleButtonClick(pressedButtonsIds);
        }

        view.update();

        return cycleState;
    }
}
