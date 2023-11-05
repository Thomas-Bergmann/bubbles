package de.hatoka.bubbles.bubble.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BubbleRO
{
    @JsonProperty("refLocal")
    private String refLocal;
    @JsonProperty("refGlobal")
    private String refGlobal;
    @JsonProperty("resourceURI")
    private String resourceURI;

    private BubbleDataRO data;

    public BubbleRO()
    {
    }

    public String getRefLocal()
    {
        return refLocal;
    }


    public void setRefLocal(String refLocal)
    {
        this.refLocal = refLocal;
    }


    public String getRefGlobal()
    {
        return refGlobal;
    }


    public void setRefGlobal(String refGlobal)
    {
        this.refGlobal = refGlobal;
    }

    public String getResourceURI()
    {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI)
    {
        this.resourceURI = resourceURI;
    }

    public BubbleDataRO getData()
    {
        return data;
    }

    public void setData(BubbleDataRO data)
    {
        this.data = data;
    }
}
