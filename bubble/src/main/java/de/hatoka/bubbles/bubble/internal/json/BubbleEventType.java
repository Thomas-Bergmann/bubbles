package de.hatoka.bubbles.bubble.internal.json;

import de.hatoka.bubbles.bubble.capi.event.history.BubbleEvent;

public enum BubbleEventType
{
	root(BubbleEvent.class, new JsonSerializer<BubbleEvent>(BubbleEvent.class))
    ;
    private final Class<? extends BubbleEvent> eventClass;
    private final JsonSerializer<? extends BubbleEvent> serializer;

    private BubbleEventType(Class<? extends BubbleEvent> eventClass, JsonSerializer<? extends BubbleEvent> serializer)
    {
        this.eventClass = eventClass;
        this.serializer = serializer;
    }

    public String serialize(Object o)
    {
        return serializer.serialize(o);
    }

    public BubbleEvent deserialize(String data)
    {
        return serializer.deserialize(data);
    }

    public static BubbleEventType valueOf(Class<?> objectClass)
    {
        for(BubbleEventType type : BubbleEventType.values())
        {
            if (type.eventClass.equals(objectClass))
            {
                return type;
            }
        }
        return null;
    }
    
    public static String serializeEvent(BubbleEvent event)
    {
        return BubbleEventType.valueOf(event.getClass()).serialize(event);
    }
}
