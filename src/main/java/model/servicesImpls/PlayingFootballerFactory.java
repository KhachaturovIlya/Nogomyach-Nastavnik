package model.servicesImpls;

import model.entityImpls.PlayingFootballer;
import model.entityInterfaces.IFootballerProfile;
import model.entityInterfaces.IPlayingFootballer;
import model.servicesInterfaces.IPlayingFootballerFactory;
import model.subclasses.Role;

public class PlayingFootballerFactory implements IPlayingFootballerFactory {
    @Override
    public IPlayingFootballer produce(IFootballerProfile player, Role role) {
        return new PlayingFootballer(player.allCharacteristics(), role);
    }

    @Override
    public IPlayingFootballer produce(IFootballerProfile player) {
        Role role = player.preferedRoles().getFirst();
        return produce(player, role);
    }
}