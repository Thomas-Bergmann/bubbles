package de.hatoka.bubbles.human.internal.remote;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.FlexRelation;
import de.hatoka.bubbles.human.capi.business.RelationType;
import de.hatoka.bubbles.human.capi.remote.MarriageDataRO;

@Component
public class MarriageROMapper
{
    public MarriageDataRO apply(FlexRelation relation)
    {
        MarriageDataRO data = new MarriageDataRO();
        data.setPartner1LocalRef(relation.getHuman1().getRef().getLocalRef());
        data.setPartner2LocalRef(relation.getHuman2().getRef().getLocalRef());
        data.setDateOfStart(relation.getStartDate().toString());
        data.setDateOfEnd(relation.getEndDate().toString());
        return data;
    }

    public List<MarriageDataRO> apply(Collection<FlexRelation> relations)
    {
        return relations.stream().filter(r -> RelationType.MARRIED_WITH.equals(r.getType())).map(this::apply).toList();
    }
}
