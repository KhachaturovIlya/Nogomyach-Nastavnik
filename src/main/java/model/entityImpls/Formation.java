package model.entityImpls;

import model.entityInterfaces.IFormation;
import model.subclasses.Role;

public class Formation implements IFormation {
    private String _textDescription;
    private Role[] _roles;

    @Override
    public String textDescription() {
        return _textDescription;
    }

    @Override
    public Role[] roles() {
        return _roles;
    }

    @Override
    public void setTextDescription(String textDescription) {
        _textDescription = textDescription;
    }

    @Override
    public void setRoles(Role[] roles) {
        _roles = roles;
    }
}