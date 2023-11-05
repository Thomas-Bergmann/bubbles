package de.hatoka.bubbles.bubble.internal.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BubbleDao extends JpaRepository<BubblePO, Long>
{
    public Optional<BubblePO> findByAbbreviation(String abbreviation);

    public Collection<BubblePO> findByUserref(String userRef);
}