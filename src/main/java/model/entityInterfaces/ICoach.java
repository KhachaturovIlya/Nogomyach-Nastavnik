package model.entityInterfaces;

import model.components.IClubMember;
import model.components.IHasAge;
import model.components.IHasName;
import model.components.IHasNationality;

public interface ICoach extends IHasName, IClubMember, IHasAge, IHasNationality {}