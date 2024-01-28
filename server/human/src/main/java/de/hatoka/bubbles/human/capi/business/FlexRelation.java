package de.hatoka.bubbles.human.capi.business;

import de.hatoka.common.capi.value.IncompleteDate;

public interface FlexRelation
{
    RelationType getType();
    IncompleteDate getStartDate();
    IncompleteDate getEndDate();
    HumanBO getHuman1();
    HumanBO getHuman2();
}
