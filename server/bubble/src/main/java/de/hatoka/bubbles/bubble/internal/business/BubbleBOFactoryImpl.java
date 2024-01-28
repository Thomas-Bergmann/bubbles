package de.hatoka.bubbles.bubble.internal.business;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;

@Component
public class BubbleBOFactoryImpl implements BubbleBOFactory
{
    @Lookup
    @Override
    public BubbleBO get(BubbleRef ref)
    {
        // done by @Lookup
        return null;
    }
}
