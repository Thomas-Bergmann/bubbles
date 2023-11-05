package de.hatoka.bubbles.bubble.capi.event;

import de.hatoka.bubbles.bubble.capi.event.history.BubbleEvent;

public class IllegalBubbleEventException extends IllegalArgumentException
{
    private static final long serialVersionUID = -7242408088884043613L;
    private final BubbleEvent event;

    public IllegalBubbleEventException(String message, BubbleEvent event)
    {
        super(message);
        this.event = event;
    }

    public BubbleEvent getEvent()
    {
        return event;
    }
}
