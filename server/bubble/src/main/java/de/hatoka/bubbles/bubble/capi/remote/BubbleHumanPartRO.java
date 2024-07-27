package de.hatoka.bubbles.bubble.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class BubbleHumanPartRO
{
    @JsonProperty("humanLocalRef")
    @NotNull
    private String humanLocalRef;
    @JsonProperty("isStillPart")
    @NotNull
    private boolean isStillPart;

    public boolean isStillPart()
    {
        return isStillPart;
    }

    public void setStillPart(boolean stillPart)
    {
        isStillPart = stillPart;
    }

    public String getHumanLocalRef()
    {
        return humanLocalRef;
    }

    public void setHumanLocalRef(String humanLocalRef)
    {
        this.humanLocalRef = humanLocalRef;
    }
}
