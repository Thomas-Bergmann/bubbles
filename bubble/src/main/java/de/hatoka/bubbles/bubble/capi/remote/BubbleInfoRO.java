package de.hatoka.bubbles.bubble.capi.remote;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BubbleInfoRO
{
    @JsonProperty("abbreviation")
    @NotNull
    private String abbreviation;

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }
}
