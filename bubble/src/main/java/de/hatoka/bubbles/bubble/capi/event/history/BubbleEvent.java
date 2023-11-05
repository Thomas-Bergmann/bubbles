package de.hatoka.bubbles.bubble.capi.event.history;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BubbleEvent
{
    /**
     * @return true of content is visible to users
     */
    @JsonIgnore
    public boolean isPublic();
}
