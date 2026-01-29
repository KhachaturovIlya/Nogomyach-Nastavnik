package model.entityInterfaces;

import model.components.IHasSize;

public interface IField extends IHasSize {
    short addPlayer(IPlayingFootballer player);
    void substitutePlayer(short ID, IPlayingFootballer playerFromBench);
    IBall Ball();
}