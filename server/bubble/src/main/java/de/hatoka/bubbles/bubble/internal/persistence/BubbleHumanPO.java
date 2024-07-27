package de.hatoka.bubbles.bubble.internal.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bubble_humans", uniqueConstraints = { @UniqueConstraint(columnNames = { "bubble_id", "human_ref" }) })
public class BubbleHumanPO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Internal identifier for persistence only
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Initial owner of the bubble (globalRef of user)
     */
    @NotNull
    @Column(name = "bubble_id", nullable = false)
    private Long bubble;

    /**
     * External key of bubble
     */
    @NotNull
    @Column(name = "human_ref", nullable = false)
    private String humanRef;

    @Column(name = "still_part", nullable = false)
    private boolean stillPart = true;

    public String getHumanRef()
    {
        return humanRef;
    }

    public void setHumanRef(String humanRef)
    {
        this.humanRef = humanRef;
    }

    public Long getBubbleId()
    {
        return bubble;
    }

    public void setBubbleId(Long bubbleId)
    {
        this.bubble = bubbleId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BubbleHumanPO that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    public boolean isStillPart()
    {
        return stillPart;
    }

    public void setStillPart(boolean stillPart)
    {
        this.stillPart = stillPart;
    }
}
