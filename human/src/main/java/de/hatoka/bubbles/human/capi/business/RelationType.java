package de.hatoka.bubbles.human.capi.business;

public enum RelationType
{
    PARENT_OF("CHILD_OF", false, false), CHILD_OF("PARENT_OF", false, true), MARRIED_WITH("MARRIED_WITH", true, true);

    private final String reverseRelation;
    private final boolean isBiDirectional;
    private final boolean isReverse;

    private RelationType(String reverseRelation, boolean isBiDirectional, boolean isReverse)
    {
        this.reverseRelation = reverseRelation;
        this.isBiDirectional = isBiDirectional;
        this.isReverse = isReverse;
    }

    public RelationType getReverseRelation()
    {
        return RelationType.valueOf(reverseRelation);
    }

    public boolean isBiDirectional()
    {
        return isBiDirectional;
    }

    public boolean isReverse()
    {
        return isReverse;
    }
}
