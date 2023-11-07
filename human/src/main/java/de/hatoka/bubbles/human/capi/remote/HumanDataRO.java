package de.hatoka.bubbles.human.capi.remote;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.hatoka.common.capi.value.IncompleteDate;

public class HumanDataRO
{
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("userRef")
    @NotNull
    private String userRef;

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    
    @JsonProperty("dateOfDeath")
    private String dateOfDeath;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserRef()
    {
        return userRef;
    }

    public void setUserRef(String userRef)
    {
        this.userRef = userRef;
    }

    @JsonIgnore
    public IncompleteDate getDateOfBirth()
    {
        return IncompleteDate.valueOf(dateOfBirth);
    }

    @JsonIgnore
    public void setDateOfBirth(IncompleteDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth.toString();
    }

    @JsonIgnore
    public IncompleteDate getDateOfDeath()
    {
        return IncompleteDate.valueOf(dateOfDeath);
    }

    @JsonIgnore
    public void setDateOfDeath(IncompleteDate dateOfDeath)
    {
        this.dateOfDeath = dateOfDeath.toString();
    }
}
