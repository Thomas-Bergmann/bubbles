package de.hatoka.bubbles.human.capi.remote;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HumanInfoRO
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
