package de.hatoka.bubbles.human.internal.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanDao extends JpaRepository<HumanPO, Long>
{
    public Optional<HumanPO> findByAbbreviation(String abbreviation);

    public Collection<HumanPO> findByUserref(String userRef);
}