package de.hatoka.bubbles.human.capi.business;

import java.util.Objects;

public class HumanRef
{
    private static final String REF_PREFIX = "human:";
    private final String abbreviation;

    public static HumanRef globalRef(String globalRef)
    {
        if (!globalRef.startsWith(REF_PREFIX))
        {
            throw new IllegalArgumentException("ref '"+globalRef+"' is not a human");
        }
        return new HumanRef(globalRef.substring(REF_PREFIX.length()));
    }

    public static HumanRef localRef(String humanAbbreviation)
    {
        return new HumanRef(humanAbbreviation);
    }

    private HumanRef(String humanAbbreviation)
    {
        this.abbreviation = humanAbbreviation;
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
        HumanRef other = (HumanRef)obj;
        return Objects.equals(abbreviation, other.abbreviation);
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }
}
