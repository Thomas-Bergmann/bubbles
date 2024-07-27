package de.hatoka.bubbles.bubble.capi.business;

import de.hatoka.bubbles.human.capi.business.HumanRef;

/**
 * Define the period of time a human was or is part of the bubble. A human can be part of the bubble multiple times.
 */
public interface Membership
{
    /**
     * @return reference to human
     */
    HumanRef getHumanRef();

    /**
     * @return true if this relation is currently active
     */
    boolean isStillPartOf();

    /**
     * Set membership active or inactive
     * @param active
     * @return the new membership
     */
    Membership setActive(boolean active);
}
