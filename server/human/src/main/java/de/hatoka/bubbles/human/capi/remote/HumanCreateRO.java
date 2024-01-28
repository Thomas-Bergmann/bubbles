package de.hatoka.bubbles.human.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class HumanCreateRO extends HumanDataRO
{
    @JsonProperty("userRef")
    @NotNull
    private String userRef;

    public String getUserRef()
    {
        return userRef;
    }

    public void setUserRef(String userRef)
    {
        this.userRef = userRef;
    }
}
