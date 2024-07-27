package de.hatoka.bubbles.bubble.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class BubbleMemberCreateRO
{
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
}
