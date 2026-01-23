package presenter.impl;

import presenter.ILangService;
import presenter.IPresenter;
import presenter.Scene;
import presenter.impl.commands.ChangeSceneCmd;
import presenter.impl.commands.QuitCmd;
import presenter.impl.commands.ShiftWidgetStateCmd;
import presenter.impl.widget.Button;
import presenter.impl.widget.Container;
import presenter.impl.widget.Widget;
import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.Vector2;
import shared.VisualWidgetDTO;
import view.Action;
import view.IView;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultPresenter implements IPresenter {
    // Fields:

    private boolean cycleState = true;

    private final IView view;

    private final Map<String, Map<Integer, Widget>> scenes;
    private Map<Integer, Widget> widgets = null;

    private final CommandLibrary commandLibrary = new CommandLibrary();
    private final ILangService langService;

    // Private DTO methods:

    private VisualWidgetDTO toVisualDtoFlatten(Widget widget, Vector2 globalNormalizedPosition,
                                               double parentWidth, double parentHeight) {
        return new VisualWidgetDTO(
            widget.getShape().compressedCopy(parentWidth, parentHeight),
            widget.getShapeColor(),
            langService.getText(widget.getTextId()),
            widget.getTextColor(),
            widget.getTextType(),
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

    public Map<Integer, Widget> getWidgets() {
        return widgets;
    }

    public void loadNewScene(String sceneName) {
        System.out.println("Loading Scene " + sceneName);
        widgets = this.scenes.get(sceneName);
    }

    public void shutdown() {
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
        try {
            commandLibrary.registerCommand("EXIT",
                new QuitCmd(this)
            );
        } catch (Exception e) {
            System.err.println("Failed to load exit command!");
        }

        try {
            commandLibrary.registerCommand("SHIFT_WIDGET_STATE",
                new ShiftWidgetStateCmd(this)
            );
        } catch (Exception e) {
            System.err.println("Failed to load SHIFT_WIDGET_STATE!");
        }

        try {
            commandLibrary.registerCommand("LOAD_NEW_SCENE",
                new ChangeSceneCmd(this)
            );
        } catch (Exception e) {
            System.err.println("Failed to load LOAD_NEW_SCENE!");
        }
    }

    public static Widget findWidgetByPath(Widget current, List<Integer> ids) {
        if (current instanceof Container container) {
            return container.findChild(ids);
        } else if (ids.size() > 1) {
            throw new RuntimeException("Tried to call children not in container class!");
        } else if (ids.size() == 1 && current.getId() == ids.getFirst()) {
            return current;
        }
        return null;
    }

    public static Widget findWidgetByPath(Widget current, String nameId) {
        if (current instanceof Container container) {
            return container.findChild(nameId);
        }
        return null;
    }

    private void handleWidgetAction(Widget widget) throws Exception {
        if (widget instanceof Button button) {
            for (String command : button.getClickActions()) {
                try {
                    commandLibrary.getCommand(command).execute(button.getActionContext());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void handleButtonClick(List<Integer> ids) throws Exception {
        if (ids == null || ids.isEmpty()) return;

        Widget current = widgets.get(ids.getFirst());
        if (current == null) throw new RuntimeException("No widget with id " + ids.getFirst());

        current = findWidgetByPath(current, ids);

        handleWidgetAction(current);
    }

    public void shiftWidgetState(String widgetId) {
        if (widgetId == null) return;

        for (Widget widget : widgets.values()) {
            Widget found = findWidgetByPath(widget, widgetId);
            if (found != null) {
                if (found.isActive()) {
                    found.disable();
                } else {
                    found.enable();
                }
                break;
            }
        }
    }

    private void processActions(List<Action> actions) throws Exception {
        for  (Action action : actions) {
            System.out.println("Working with action: " + action);
            switch (action) {
                case Action.WidgetClicked(List<Integer> ids) -> {
                    handleButtonClick(ids);
                }
                case Action.Quit() -> {
                    shutdown();
                }
                case Action.shiftWidgetState(String stringId) -> {
                    shiftWidgetState(stringId);
                }
            }
        }
    }

    // Public methods:

    public DefaultPresenter(IView view, Map<String, Map<Integer, Widget>> scenes, ILangService langService) {
        this.view = view;

        this.scenes = scenes;

        this.widgets = scenes.get(Scene.MAIN_MENU.name());

        this.langService = langService;

        try {
            this.langService.loadFile(Path.of("common/ru"));
        } catch (Exception e) {
            System.err.println("Failed to load common lang files: " + e);
        }
        for (Scene scene : Scene.values()) {
            try {
                this.langService.loadFile(Path.of("scenes/" + scene.name().toLowerCase() + "/ru"));
            } catch (Exception e) {
                System.err.println("Failed to load scene files: " + e);
            }
        }

        this.loadCommands();
    }

    @Override
    public boolean run(double deltaTime) throws Exception {
        UserInterfaceDTO uiDTO = new UserInterfaceDTO(prepareVisualDTO());

        view.renderUI(uiDTO);

        processActions(view.getActions(prepareActionDTO()));

        view.update();

        return cycleState;
    }
}
