package view;

import shared.Vector2;

public sealed interface Action {

    record WidgetClicked(int id) implements Action {}

    record Quit() implements Action {}
}