package de.hatoka.bubbles.bubble.capi.remote;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BubbleDataRO
{
    @JsonProperty("name")
    @NotNull
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
