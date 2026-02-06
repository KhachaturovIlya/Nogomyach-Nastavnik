package shared;

import java.util.List;

public record ActionWidgetDTO (
    List<Integer> ids,
    Shape shape,
    Vector2 position
) {}
