package de.hatoka.bubbles.human.internal.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRelationDao extends JpaRepository<HumanRelationPO, Long>
{
    List<HumanRelationPO> findByHuman1(Long human1);
    List<HumanRelationPO> findByHuman2(Long human2);
}