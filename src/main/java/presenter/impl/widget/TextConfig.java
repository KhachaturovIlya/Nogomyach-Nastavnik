package presenter.impl.widget;

import shared.Color;
import shared.TextType;

import java.util.List;

public record TextConfig(
    String id,
    Color color,
    TextType type,
    List<DataBinding> bindings
) {
    public static TextConfig empty() {
        return new TextConfig("NO_TEXT", Color.transparent(), TextType.DEFAULT, List.of());
    }
}
