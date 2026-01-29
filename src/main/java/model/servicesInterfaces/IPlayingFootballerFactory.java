package model.servicesInterfaces;

import model.entityInterfaces.IFootballerProfile;
import model.entityInterfaces.IPlayingFootballer;
import model.subclasses.Role;

public interface IPlayingFootballerFactory {
    IPlayingFootballer produce(IFootballerProfile player, Role role);
    IPlayingFootballer produce(IFootballerProfile player);
}