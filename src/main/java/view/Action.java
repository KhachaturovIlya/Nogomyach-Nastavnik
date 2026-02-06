package view;

import shared.Vector2;

import java.util.List;

public sealed interface Action {

    record WidgetClicked(List<Integer> ids) implements Action {}

    record Quit() implements Action {}

    record shiftWidgetState(String stringId) implements Action {}
}