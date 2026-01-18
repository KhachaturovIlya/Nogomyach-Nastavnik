package shared;

import java.util.List;

public record UserInterfaceDTO (
    List<VisualWidgetDTO> widgetDTOS
) {}
