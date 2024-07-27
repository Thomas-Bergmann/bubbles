package de.hatoka.bubbles.bubble.capi.remote;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BubbleDataRO
{
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("userRef")
    @NotNull
    private String userRef;

    @JsonProperty("members")
    private List<BubbleHumanPartRO> humans;

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

    public List<BubbleHumanPartRO> getHumans()
    {
        return humans;
    }

    public void setHumans(List<BubbleHumanPartRO> humans)
    {
        this.humans = humans;
    }
}
