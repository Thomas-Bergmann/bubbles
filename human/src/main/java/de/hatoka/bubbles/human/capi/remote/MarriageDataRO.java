package de.hatoka.bubbles.human.capi.remote;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class MarriageDataRO
{
    @NotNull
    @JsonProperty("partner1")
    private String partner1LocalRef;
    @NotNull
    @JsonProperty("partner2")
    private String partner2LocalRef;
    @JsonProperty("dateOfStart")
    private String dateOfStart;
    @JsonProperty("dateOfEnd")
    private String dateOfEnd;

    public String getDateOfStart()
    {
        return dateOfStart;
    }

    public void setDateOfStart(String dateOfStart)
    {
        this.dateOfStart = dateOfStart;
    }

    public String getDateOfEnd()
    {
        return dateOfEnd;
    }

    public void setDateOfEnd(String dateOfEnd)
    {
        this.dateOfEnd = dateOfEnd;
    }

    public String getPartner1LocalRef()
    {
        return partner1LocalRef;
    }

    public void setPartner1LocalRef(String partner1LocalRef)
    {
        this.partner1LocalRef = partner1LocalRef;
    }

    public String getPartner2LocalRef()
    {
        return partner2LocalRef;
    }

    public void setPartner2LocalRef(String partner2LocalRef)
    {
        this.partner2LocalRef = partner2LocalRef;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(dateOfEnd, dateOfStart, partner1LocalRef, partner2LocalRef);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MarriageDataRO other = (MarriageDataRO)obj;
        return Objects.equals(dateOfEnd, other.dateOfEnd) && Objects.equals(dateOfStart, other.dateOfStart)
                        && Objects.equals(partner1LocalRef, other.partner1LocalRef)
                        && Objects.equals(partner2LocalRef, other.partner2LocalRef);
    }

}
