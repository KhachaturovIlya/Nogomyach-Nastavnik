package model.interfaces;

import shared.Vector3;

public interface IHasSize {
    Vector3 getSize();
    double width();
    double length();
    double height();
}