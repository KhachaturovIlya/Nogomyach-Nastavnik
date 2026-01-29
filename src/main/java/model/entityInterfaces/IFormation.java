package model.entityInterfaces;

import model.subclasses.Role;

public interface IFormation {
    String textDescription();
    Role[] roles();
    void setTextDescription(String textDescription);
    void setRoles(Role[] roles);
}