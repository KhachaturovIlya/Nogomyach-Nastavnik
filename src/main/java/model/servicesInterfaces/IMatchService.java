package model.servicesInterfaces;

import model.entityInterfaces.IPlayingFootballer;
import shared.Vector3;

public interface IMatchService {
    void substitutePlayer(String team, short number, IPlayingFootballer player);
    Vector3[] playerPositions();
    Vector3 ballPosition();
}