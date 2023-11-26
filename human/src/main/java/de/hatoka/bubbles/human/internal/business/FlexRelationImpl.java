package de.hatoka.bubbles.human.internal.business;

import de.hatoka.bubbles.human.capi.business.FlexRelation;
import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.RelationType;
import de.hatoka.bubbles.human.internal.persistence.HumanRelationFlexPO;
import de.hatoka.common.capi.value.IncompleteDate;

public class FlexRelationImpl implements FlexRelation
{
    private final HumanBORepository repository;
    private final HumanRelationFlexPO relation;

    public FlexRelationImpl(HumanRelationFlexPO relation, HumanBORepository repository)
    {
        this.relation = relation;
        this.repository = repository;
    }

    @Override
    public RelationType getType()
    {
        return relation.getType();
    }

    @Override
    public IncompleteDate getStartDate()
    {
        return IncompleteDate.valueOf(relation.getDateStart());
    }

    @Override
    public IncompleteDate getEndDate()
    {
        return IncompleteDate.valueOf(relation.getDateEnd());
    }

    @Override
    public HumanBO getHuman1()
    {
        return repository.getHuman(relation.getHuman1());
    }

    @Override
    public HumanBO getHuman2()
    {
        return repository.getHuman(relation.getHuman2());
    }

}
