package de.hatoka.bubbles.human.internal.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRelationFixDao extends JpaRepository<HumanRelationFixPO, Long>
{
    List<HumanRelationFixPO> findByHuman1(Long human1);
    List<HumanRelationFixPO> findByHuman2(Long human2);
}