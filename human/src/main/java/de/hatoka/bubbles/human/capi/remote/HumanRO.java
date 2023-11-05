package de.hatoka.bubbles.human.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HumanRO
{
    @JsonProperty("refLocal")
    private String refLocal;
    @JsonProperty("refGlobal")
    private String refGlobal;
    @JsonProperty("resourceURI")
    private String resourceURI;

    private HumanInfoRO info;
    private HumanDataRO data;

    public HumanRO()
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


	public HumanInfoRO getInfo() {
		return info;
	}


	public void setInfo(HumanInfoRO info) {
		this.info = info;
	}

    public String getResourceURI()
    {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI)
    {
        this.resourceURI = resourceURI;
    }

    public HumanDataRO getData()
    {
        return data;
    }

    public void setData(HumanDataRO data)
    {
        this.data = data;
    }
}
