package de.hatoka.bubbles.bubble.internal.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BubbleDao extends JpaRepository<BubblePO, Long>
{
    default Optional<BubblePO> findByExternalID(String externalID)
    {
        return findByBubbleext(externalID);
    }
    /**
     * @param externalID
     * @return bubble found by externalid
     * @deprecated don't use it outside only for JpaRepository use {@link #findByExternalID(String)}
     */
    @Deprecated
    Optional<BubblePO> findByBubbleext(String externalID);

    Collection<BubblePO> findByUserref(String userRef);
}