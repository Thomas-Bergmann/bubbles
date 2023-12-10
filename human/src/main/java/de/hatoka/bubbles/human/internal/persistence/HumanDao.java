package de.hatoka.bubbles.human.internal.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.hatoka.bubbles.human.capi.business.RelationType;

public interface HumanDao extends JpaRepository<HumanPO, Long>
{
    default Optional<HumanPO> findByExternalID(String externalID)
    {
        return findByHumanext(externalID);
    }

    /**
     * @param humanExternalID
     * @return human found by externalid
     * @deprecated don't use it outside only for JpaRepository use {@link #findByExternalID(String)}
     */
    @Deprecated
    Optional<HumanPO> findByHumanext(String humanExternalID);

    Collection<HumanPO> findByUserref(String userRef);

    @Query("SELECT humans"
                    + " FROM de.hatoka.bubbles.human.internal.persistence.HumanPO as humans"
                    + " , de.hatoka.bubbles.human.internal.persistence.HumanRelationFixPO as relations"
                    + " where relations.human1 = humans.id"
                    + " and relations.human2 = :childID"
                    + " and relations.type = :type")
    Collection<HumanPO> findByChildRef(@Param("childID") Long childID, @Param("type") RelationType type);
}