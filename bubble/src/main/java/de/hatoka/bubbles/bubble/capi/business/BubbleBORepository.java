package de.hatoka.bubbles.bubble.capi.business;

import java.util.Collection;
import java.util.Optional;

import de.hatoka.user.capi.business.UserRef;

public interface BubbleBORepository
{
    /**
     * @return all registered bubbles of an user
     */
    Collection<BubbleBO> getBubbles(UserRef userRef);

    /**
     * Creates a bubble.
     * @param bubbleRef bubble reference for user
     * @param name name of bubble
     * @return created bubble
     */
    BubbleBO createBubble(BubbleRef bubbleRef, String name, UserRef userRef);

    /**
     * @param bubbleRef bubble
     * @return bubble that must exist
     */
    BubbleBO getBubble(BubbleRef bubbleRef);

    /**
     * Find a bubble
     * @param bubbleRef bubble
     * @return bubble found or not
     */
    Optional<BubbleBO> findBubble(BubbleRef bubbleRef);

    /**
     * resolved a bubble by given internal id, 
     *   especially if the caller knows that the bubble exists (like by foreign keys)
     * @param internalBubbleId internal bubble id for persistence
     * @return bubble
     */
    default BubbleBO getBubble(Long internalBubbleId)
    {
        return findBubble(internalBubbleId).get();
    }

    /**
     * Find a bubble
     * @param internalBubbleId internal bubble id for persistence
     * @return bubble found or not
     */
    Optional<BubbleBO> findBubble(Long internalBubbleId);

    /**
     * Removes all users of this repository
     */
    default void clear()
    {
        getAllBubbles().forEach(BubbleBO::remove);
    }

    /**
     * @return all registered bubbles
     */
    Collection<BubbleBO> getAllBubbles();
}
