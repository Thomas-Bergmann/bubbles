package de.hatoka.bubbles.bubble.capi.business;

import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.user.capi.business.UserRef;

import java.util.List;

/**
 * A bubble can contain multiple humans.
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

    /**
     * @return a list of humans, which are part of the bubble
     */
    List<Membership> getMembers();

    /**
     * adds a human to the bubble
     */
    Membership add(HumanRef humanRef);

    /**
     * Remove human from bubble (afterward it looks that the human was never part of the bubble)
     * @param humanRef
     */
    void remove(HumanRef humanRef);

    /**
     * @param humanRef
     * @return true if human is or was member of bubble
     */
    boolean exists(HumanRef humanRef);
}
