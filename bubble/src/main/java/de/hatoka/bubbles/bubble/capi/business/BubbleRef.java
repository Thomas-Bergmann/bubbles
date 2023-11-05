package de.hatoka.bubbles.bubble.capi.business;

import java.util.Objects;

public class BubbleRef
{
    private static final String REF_PREFIX = "bubble:";
    private final String abbreviation;

    public static BubbleRef globalRef(String globalRef)
    {
        if (!globalRef.startsWith(REF_PREFIX))
        {
            throw new IllegalArgumentException("ref '"+globalRef+"' is not a bubble");
        }
        return new BubbleRef(globalRef.substring(REF_PREFIX.length()));
    }

    public static BubbleRef localRef(String bubbleAbbreviation)
    {
        return new BubbleRef(bubbleAbbreviation);
    }

    private BubbleRef(String bubbleAbbreviation)
    {
        this.abbreviation = bubbleAbbreviation;
    }

    @Override
    public String toString()
    {
        return getGlobalRef();
    }

    public String getLocalRef()
    {
        return abbreviation;
    }

    public String getGlobalRef()
    {
        return REF_PREFIX + getLocalRef();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(abbreviation);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BubbleRef other = (BubbleRef)obj;
        return Objects.equals(abbreviation, other.abbreviation);
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }
}
