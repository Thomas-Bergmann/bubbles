package de.hatoka.bubbles.human.internal.business;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.internal.persistence.HumanPO;

public interface HumanBOFactory
{
    HumanBO get(HumanRef ref);
    
    default HumanBO get(HumanPO po)
    {
        return get(HumanRef.localRef(po.getExternalID()));
    }
}
