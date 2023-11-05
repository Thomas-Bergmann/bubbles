package de.hatoka.bubbles.human.internal.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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
}