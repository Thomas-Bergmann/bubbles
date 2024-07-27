package de.hatoka.bubbles.bubble.internal.remote;

import java.util.Collection;
import java.util.List;

import de.hatoka.bubbles.bubble.capi.business.Membership;
import de.hatoka.bubbles.bubble.capi.remote.BubbleHumanPartRO;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleDataRO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleRO;

@Component
public class BubbleBO2RO
{
    public BubbleRO apply(BubbleBO bubble)
    {
        BubbleDataRO data = new BubbleDataRO();
        data.setName(bubble.getName());
        data.setUserRef(bubble.getUserRef().getGlobalRef());
        data.setHumans(apply(bubble.getMembers()));
        BubbleRO result = new BubbleRO();
        result.setRefGlobal(bubble.getRef().getGlobalRef());
        result.setRefLocal(bubble.getRef().getLocalRef());
        result.setResourceURI("/bubbles/" + bubble.getRef().getLocalRef());
        result.setData(data);
        return result;
    }

    private List<BubbleHumanPartRO> apply(List<Membership> members)
    {
        return members.stream().map(this::apply).toList();
    }

    private BubbleHumanPartRO apply(Membership membership)
    {
        BubbleHumanPartRO result = new BubbleHumanPartRO();
        result.setHumanLocalRef(membership.getHumanRef().getLocalRef());
        result.setStillPart(membership.isStillPartOf());
        return result;
    }

    public List<BubbleRO> apply(Collection<BubbleBO> bubbles)
    {
        return bubbles.stream().map(this::apply).toList();
    }
}
