package presenter.impl;

import model.repoInterfaces.ITeamRepository;
import presenter.ILangService;
import presenter.IPresenter;
import presenter.Scene;
import presenter.impl.widget.*;
import shared.*;
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
    private final ITeamRepository repository;

    private final Map<String, Map<Integer, Widget>> scenes;
    private Map<Integer, Widget> widgets = null;

    private final CommandLibrary commandLibrary;
    private final ILangService langService;

    private float volume = -10.f;

    // Private DTO methods:

    private VisualWidgetDTO toVisualDtoFlatten(Widget widget, Vector2 globalNormalizedPosition,
                                               double parentWidth, double parentHeight) {
        TextConfig textConfig = widget.getTextConfig();

        return new VisualWidgetDTO(
            widget.getShape().compressedCopy(parentWidth, parentHeight),
            widget.getShapeColor(),
            processText(textConfig),
            textConfig.color(),
            textConfig.type(),
            globalNormalizedPosition,
            widget instanceof Button
        );
    }

    private String processText(TextConfig textConfig) {
        String template = langService.getText(textConfig.id());

        if (textConfig.bindings() == null || textConfig.bindings().isEmpty()) {
            return template;
        }

        List<String> context = new ArrayList<>();

        for (DataBinding binding : textConfig.bindings()) {
            try {
                String data = commandLibrary.getContextCommand(binding.query()).execute(binding.subjectId()).getFirst();
                context.add(data != null ? data : "???");
            } catch (Exception e) {
                return template;
            }
        }

        try {
            return String.format(template, context.toArray());
        } catch (Exception e) {
            return template;
        }
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
                if (container instanceof DynamicContainer dynamicContainer) {
                    try {
                        dynamicContainer.rebuild(commandLibrary.getContextCommand(dynamicContainer.getTemplatesInfo().query()).execute(
                            dynamicContainer.getTemplatesInfo().subjectId()
                        ));
                    } catch (Exception e) {
                        System.out.println("Exception while rebuilding template " + dynamicContainer.getTemplatesInfo().query());
                    }
                }

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

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
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
        if (current.getName().equals(nameId)) {
            return current;
        }

        if (current instanceof Container container) {
            return container.findChild(nameId);
        }
        return null;
    }

    private void handleWidgetAction(Widget widget) throws Exception {
        if (widget instanceof Button button) {
            List<DataBinding> clickActions = button.getClickActions();
            for (DataBinding binding : clickActions) {
                try {
                    commandLibrary.getCommand(binding.query()).execute(binding.subjectId());
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

    public DefaultPresenter(IView view, ITeamRepository teamRepository, Map<String, Map<Integer, Widget>> scenes, ILangService langService) {
        this.view = view;
        this.repository = teamRepository;

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

        CommandFactory commandFactory = new CommandFactory(this);
        this.commandLibrary = commandFactory.createCommandLibrary();
    }

    @Override
    public boolean run(double deltaTime) throws Exception {
        view.setVolume(volume);

        UserInterfaceDTO uiDTO = new UserInterfaceDTO(prepareVisualDTO());

        view.renderUI(uiDTO);

        processActions(view.getActions(prepareActionDTO()));

        view.update();

        return cycleState;
    }
}
