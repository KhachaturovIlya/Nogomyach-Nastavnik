package presenter.impl;

import presenter.IPresenter;
import shared.ActionWidgetDTO;
import shared.VisualWidgetDTO;
import view.IView;
import presenter.impl.Widget;

import java.util.ArrayList;
import java.util.List;

public class DefaultPresenter implements IPresenter {
    // Fields:

    private IView view;
    private List<Widget> buttons;
    private List<Widget> labels;

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

    // Public methods:

    public DefaultPresenter(IView view, List<Widget> buttons, List<Widget> labels) {
        this.view = view;
        this.buttons = buttons != null ? buttons : new ArrayList<>();
        this.labels = labels != null ? labels : new ArrayList<>();
    }

    @Override
    public boolean run(double deltaTime) {


        return false;
    }
}
