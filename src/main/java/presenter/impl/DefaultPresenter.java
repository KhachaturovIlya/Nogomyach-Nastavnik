package presenter.impl;

import presenter.IPresenter;
import presenter.impl.interfaces.IWidgetFileFactory;
import presenter.impl.widget.Button;
import presenter.impl.widget.Container;
import presenter.impl.widget.Widget;
import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.Vector2;
import shared.VisualWidgetDTO;
import view.Action;
import view.IView;

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
    private final Map<Integer, Widget> widgets;

    private final CommandLibrary commandLibrary = new CommandLibrary();

    // Private DTO methods:

    private static VisualWidgetDTO toVisualDtoFlatten(Widget widget, Vector2 globalNormalizedPosition,
                                               double parentWidth, double parentHeight) {
        return new VisualWidgetDTO(
            widget.getShape().compressedCopy(parentWidth, parentHeight),
            widget.getShapeColor(),
            widget.getText(),
            widget.getTextColor(),
            globalNormalizedPosition,
            widget instanceof Button
        );
    }

    private void flattenContainerToVisualDto(Map<Integer, Widget> widgets, List<VisualWidgetDTO> visualWidgetDTOs,
                             Vector2 parentNormalizedPosition, double parentWidth, double parentHeight) {
        for (Widget widget : widgets.values()) {
            Vector2 widgetGlobalNormalizedPosition = new Vector2(parentNormalizedPosition.x + widget.getNormalizedPosition().x * parentWidth,
                parentNormalizedPosition.y + widget.getNormalizedPosition().y * parentHeight);

            double widgetGlobalWidth = parentWidth * widget.getShape().getWidth();
            double widgetGlobalHeight = parentHeight * widget.getShape().getHeight();

            visualWidgetDTOs.add(toVisualDtoFlatten(widget, widgetGlobalNormalizedPosition,
                parentWidth, parentHeight));
            if (widget instanceof Container container) {
                flattenContainerToVisualDto(container.getChildren(), visualWidgetDTOs,
                    widgetGlobalNormalizedPosition, widgetGlobalWidth, widgetGlobalHeight);
            }
        }
    }

    private List<VisualWidgetDTO> prepareVisualDTO() {
        List<VisualWidgetDTO> visualWidgetDTOS = new ArrayList<>();

        for (Widget root : widgets.values()) {
            if (root.isActive()) {
                visualWidgetDTOS.add(toVisualDtoFlatten(root, root.getNormalizedPosition(), 1, 1));
                if (root instanceof Container container) {
                    flattenContainerToVisualDto(container.getChildren(), visualWidgetDTOS, container.getNormalizedPosition(),
                        container.getShape().getWidth(), container.getShape().getHeight());
                }
            }
        }

        return visualWidgetDTOS;
    }

    private static ActionWidgetDTO toActionDtoFlatten(List<Integer> ids, Widget widget, Vector2 globalNormalizedPosition,
                                                      double parentWidth, double parentHeight) {
        return new ActionWidgetDTO(
            ids,
            widget.getShape().compressedCopy(parentWidth, parentHeight),
            globalNormalizedPosition
        );
    }

    private void flattenContainerToActionDTO(List<Integer> ids, Map<Integer, Widget> widgets, List<ActionWidgetDTO> actionWidgetDTOs,
                                             Vector2 parentNormalizedPosition, double parentWidth, double parentHeight) {
        for (Widget widget : widgets.values()) {
            List<Integer> recursiveID = new ArrayList<>(ids);
            recursiveID.add(widget.getId());

            Vector2 widgetGlobalNormalizedPosition = new Vector2(parentNormalizedPosition.x + widget.getNormalizedPosition().x * parentWidth,
                parentNormalizedPosition.y + widget.getNormalizedPosition().y * parentHeight);

            double widgetGlobalWidth = parentWidth * widget.getShape().getWidth();
            double widgetGlobalHeight = parentHeight * widget.getShape().getHeight();

            actionWidgetDTOs.add(toActionDtoFlatten(recursiveID, widget, widgetGlobalNormalizedPosition,
                parentWidth, parentHeight));
            if (widget instanceof Container container) {
                flattenContainerToActionDTO(recursiveID, container.getChildren(), actionWidgetDTOs,
                    widgetGlobalNormalizedPosition, widgetGlobalWidth, widgetGlobalHeight);
            }
        }
    }

    private List<ActionWidgetDTO> prepareActionDTO() {
        List<ActionWidgetDTO> actionWidgetDTOs = new ArrayList<>();

        flattenContainerToActionDTO(new ArrayList<>(), this.widgets, actionWidgetDTOs,
            new Vector2(0, 0), 1.0, 1.0);

        return actionWidgetDTOs;
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
        commandLibrary.registerCommand("EXIT",
            this::shutdown
        );
    }

    private void handleButtonClick(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;

        Widget current = widgets.get(ids.getFirst());
        if (current == null) throw new RuntimeException("No widget with id " + ids.getFirst());

        if (current instanceof Container container) {
            current = container.findChild(ids);
        } else if (ids.size() > 1) {
            throw new RuntimeException("Tried to call children not in container class!");
        }

        if (current instanceof Button button) {
            for (String command : button.getClickActions()) {
                commandLibrary.getCommand(command).run();
            }
        }
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

    public DefaultPresenter(IView view, Map<Integer, Widget> widgets) {
        this.view = view;
        this.widgets = widgets != null ? widgets : new HashMap<>();

        this.loadCommands();
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
