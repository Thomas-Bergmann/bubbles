package de.hatoka.bubbles.human.internal.persistence;

import java.io.Serializable;

import de.hatoka.bubbles.human.capi.business.RelationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "relation_fix", uniqueConstraints = { @UniqueConstraint(columnNames = { "human_1", "human_2", "type" }) })
public class HumanRelationFixPO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Internal identifier for persistence only
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "human_rel_id")
    private Long id;

    @NotNull
    @Column(name = "human_1", nullable = false)
    private Long human1;
    @NotNull
    @Column(name = "human_2", nullable = false)
    private Long human2;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationType type;

    public HumanRelationFixPO()
    {
    }

    public Long getId()
    {
        return id;
    }

    public Long getHuman1()
    {
        return human1;
    }

    public void setHuman1(Long human1)
    {
        this.human1 = human1;
    }

    public Long getHuman2()
    {
        return human2;
    }

    public void setHuman2(Long human2)
    {
        this.human2 = human2;
    }

    public RelationType getType()
    {
        return type;
    }

    public void setType(RelationType type)
    {
        this.type = type;
    }
}
