package de.hatoka.bubbles.bubble.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class BubbleCreateRO extends BubbleDataRO
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
