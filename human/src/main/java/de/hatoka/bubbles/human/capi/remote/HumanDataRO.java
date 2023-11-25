package de.hatoka.bubbles.human.capi.remote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.hatoka.bubbles.human.capi.business.Gender;
import de.hatoka.common.capi.value.IncompleteDate;
import jakarta.validation.constraints.NotNull;

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
    @JsonProperty("gender")
    private String gender;

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

    @JsonIgnore
    public Gender getGender()
    {
        return this.gender == null? null : Gender.valueOf(this.gender);
    }

    @JsonIgnore
    public void setGender(Gender gender)
    {
        this.gender = gender == null ? null : gender.name();
    }
}
