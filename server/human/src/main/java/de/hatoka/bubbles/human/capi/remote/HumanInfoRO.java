package de.hatoka.bubbles.human.capi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HumanInfoRO
{
    @JsonProperty("age")
    private Integer age;

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }
}
