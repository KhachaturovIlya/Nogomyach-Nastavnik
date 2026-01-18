package view;

import shared.Vector2;

public sealed interface Action {

    record WidgetClicked(int id) implements Action {}

    record FieldClicked(Vector2 position) implements Action {}

    record KeyPressed(String key) implements Action {}

    record Quit() implements Action {}
}