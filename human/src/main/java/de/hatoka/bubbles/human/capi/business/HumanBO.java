package de.hatoka.bubbles.human.capi.business;

import java.util.List;

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

    void setGender(Gender gender);

    Gender getGender();

    default void addChild(HumanBO child)
    {
        addRelation(RelationType.PARENT_OF, child);
    }

    default void removeChild(HumanBO child)
    {
        removeRelation(RelationType.PARENT_OF, child);
    }

    default List<HumanBO> getChildren()
    {
        return getRelations(RelationType.PARENT_OF);
    }

    default List<HumanBO> getParents()
    {
        return getRelations(RelationType.CHILD_OF);
    }

    default void addMariageWith(HumanBO husband)
    {
        addMariageWith(husband, IncompleteDate.UNKNOWN_DATE, IncompleteDate.UNKNOWN_DATE);
    }

    void addMariageWith(HumanBO partner, IncompleteDate start, IncompleteDate end);

    void removeMariageWith(HumanBO partner);

    List<HumanBO> getMariageWith();

    List<FlexRelation> getMariageRelations();

    /**
     * "private" method to add relation
     * @param type
     * @param other
     */
    void addRelation(RelationType type, HumanBO other);

    /**
     * "private" method to remove relation
     * @param type
     * @param other
     */
    void removeRelation(RelationType type, HumanBO other);

    /**
     * "private" method to retrieve relations
     * @param type
     */
    List<HumanBO> getRelations(RelationType type);
}
