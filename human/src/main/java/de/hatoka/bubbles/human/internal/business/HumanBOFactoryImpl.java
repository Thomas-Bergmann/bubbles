package de.hatoka.bubbles.human.internal.business;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanRef;

@Component
public class HumanBOFactoryImpl implements HumanBOFactory
{
    @Lookup
    @Override
    public HumanBO get(HumanRef ref)
    {
        // done by @Lookup
        return null;
    }
}
