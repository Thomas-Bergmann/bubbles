package de.hatoka.bubbles.bubble.internal.business;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.internal.persistence.BubblePO;

public interface BubbleBOFactory
{
    BubbleBO get(BubbleRef ref);
    
    default BubbleBO get(BubblePO po)
    {
        return get(BubbleRef.localRef(po.getAbbreviation()));
    }
}
