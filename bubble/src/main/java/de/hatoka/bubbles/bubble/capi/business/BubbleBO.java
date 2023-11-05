package de.hatoka.bubbles.bubble.capi.business;

import de.hatoka.user.capi.business.UserRef;

/**
 * A bubble can reference multiple humans.
 */
public interface BubbleBO
{
    /**
     * @return unique identifier of bubble in specific context.
     */
	BubbleRef getRef();

	/**
     * @return name of bubble
     */
    String getName();

    /**
     * Removes bubble
     */
    void remove();

    /**
     * @return internal identifier for foreign keys
     */
    Long getInternalId();

    /**
     * @return owner of bubble
     */
    UserRef getUserRef();
}
