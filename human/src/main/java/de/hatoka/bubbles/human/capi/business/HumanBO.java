package de.hatoka.bubbles.human.capi.business;

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
    
}
