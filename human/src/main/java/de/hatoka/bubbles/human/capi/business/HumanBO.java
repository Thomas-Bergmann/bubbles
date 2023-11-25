package de.hatoka.bubbles.human.capi.business;

import de.hatoka.common.capi.value.IncompleteDate;
import de.hatoka.user.capi.business.UserRef;

/**
 * A human can reference multiple bubbles.
 */
public interface HumanBO
{
    /**
     * @return unique identifier of human in specific context.
     */
	HumanRef getRef();

	/**
     * @return name of human
     */
    String getName();

    /**
     * Removes human
     */
    void remove();

    /**
     * @return internal identifier for foreign keys
     */
    Long getInternalId();

    /**
     * @return owner of human
     */
    UserRef getUserRef();

    void setDateOfBirth(IncompleteDate dateOfBirth);
    IncompleteDate getDateOfBirth();
    void setDateOfDeath(IncompleteDate dateOfDeath);
    IncompleteDate getDateOfDeath();
}
