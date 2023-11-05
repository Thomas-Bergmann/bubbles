package de.hatoka.bubbles.human.capi.business;

import java.util.Collection;
import java.util.Optional;

import de.hatoka.user.capi.business.UserRef;

public interface HumanBORepository
{
    /**
     * @return all registered humans of an user
     */
    Collection<HumanBO> getHumans(UserRef userRef);

    /**
     * Creates a human.
     * @param humanRef human reference for user
     * @param name name of human
     * @return created human
     */
    HumanBO createHuman(HumanRef humanRef, String name, UserRef userRef);

    /**
     * @param humanRef human
     * @return human that must exist
     */
    HumanBO getHuman(HumanRef humanRef);

    /**
     * Find a human
     * @param humanRef human
     * @return human found or not
     */
    Optional<HumanBO> findHuman(HumanRef humanRef);

    /**
     * resolved a human by given internal id, 
     *   especially if the caller knows that the human exists (like by foreign keys)
     * @param internalHumanId internal human id for persistence
     * @return human
     */
    default HumanBO getHuman(Long internalHumanId)
    {
        return findHuman(internalHumanId).get();
    }

    /**
     * Find a human
     * @param internalHumanId internal human id for persistence
     * @return human found or not
     */
    Optional<HumanBO> findHuman(Long internalHumanId);

    /**
     * Removes all humans of this repository
     */
    default void clear()
    {
        getAllHumans().forEach(HumanBO::remove);
    }

    /**
     * @return all registered humans
     */
    Collection<HumanBO> getAllHumans();
}
