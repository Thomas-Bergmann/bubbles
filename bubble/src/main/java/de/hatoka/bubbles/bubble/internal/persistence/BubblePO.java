package de.hatoka.bubbles.bubble.internal.persistence;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bubbles", uniqueConstraints = { @UniqueConstraint(columnNames = { "abbreviation" }) })
public class BubblePO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Internal identifier for persistence only
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bubble_id")
    private Long id;

    /**
     * Initial owner of the bubble
     */
    @NotNull
    @Column(name = "user_ref", nullable = false)
    private String userref;

    /**
     * External key of bubble
     */
    @NotNull
    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    /**
     * Readable name of bubble
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public BubblePO()
    {
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(abbreviation);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BubblePO other = (BubblePO)obj;
        return Objects.equals(abbreviation, other.abbreviation);
    }

    public String getUserRef()
    {
        return userref;
    }

    public void setUserRef(String userRef)
    {
        this.userref = userRef;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }
}
