package model.components;

import shared.Vector3;

public interface IHasSize {
    void setSize(Vector3 size);
    double width();
    double length();
    double height();
}