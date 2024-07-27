package de.hatoka.bubbles.bubble.internal.persistence;

import de.hatoka.bubbles.human.capi.business.HumanRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BubbleHumanDao extends JpaRepository<BubbleHumanPO, Long>
{
    List<BubbleHumanPO> findByBubble(Long internalId);

    Optional<BubbleHumanPO> findByBubbleAndHumanRef(Long internalId, String humanGlobalRef);
}