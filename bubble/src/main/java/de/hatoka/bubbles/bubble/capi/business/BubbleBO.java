package de.hatoka.bubbles.bubble.capi.business;

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
    
}
