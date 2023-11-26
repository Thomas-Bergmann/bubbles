package de.hatoka.bubbles.human.internal.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRelationFlexDao extends JpaRepository<HumanRelationFlexPO, Long>
{
    List<HumanRelationFlexPO> findByHuman1(Long human1);
    List<HumanRelationFlexPO> findByHuman2(Long human2);
}