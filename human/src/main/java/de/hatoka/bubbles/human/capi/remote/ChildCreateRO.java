package de.hatoka.bubbles.human.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildCreateRO extends HumanDataRO
{
    @JsonProperty("userRef")
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
