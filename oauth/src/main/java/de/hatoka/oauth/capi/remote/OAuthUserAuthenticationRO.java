package de.hatoka.oauth.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.hatoka.oauth.capi.business.TokenUsage;
import jakarta.validation.constraints.NotNull;

public class OAuthUserAuthenticationRO
{
    @JsonProperty("idp")
    @NotNull
    private String idpRef;
    @JsonProperty("type")
    @NotNull
    private TokenUsage type;

    public String getIdpRef()
    {
        return idpRef;
    }

    public void setIdpRef(String idpRef)
    {
        this.idpRef = idpRef;
    }

    public TokenUsage getType()
    {
        return type;
    }

    public void setType(TokenUsage type)
    {
        this.type = type;
    }
}
