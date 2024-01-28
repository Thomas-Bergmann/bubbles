package de.hatoka.bubbles.bubble.capi.business;

import java.util.Objects;

public class BubbleRef
{
    private static final String REF_PREFIX = "bubble:";
    private final String externalID;

    public static BubbleRef globalRef(String globalRef)
    {
        if (!globalRef.startsWith(REF_PREFIX))
        {
            throw new IllegalArgumentException("ref '"+globalRef+"' is not a bubble");
        }
        return new BubbleRef(globalRef.substring(REF_PREFIX.length()));
    }

    public static BubbleRef localRef(String externalID)
    {
        return new BubbleRef(externalID);
    }

    private BubbleRef(String externalID)
    {
        this.externalID = externalID;
    }

    @Override
    public String toString()
    {
        return getGlobalRef();
    }

    public String getLocalRef()
    {
        return externalID;
    }

    public String getGlobalRef()
    {
        return REF_PREFIX + getLocalRef();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(externalID);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BubbleRef other = (BubbleRef)obj;
        return Objects.equals(externalID, other.externalID);
    }

    public String getExternalID()
    {
        return externalID;
    }
}
