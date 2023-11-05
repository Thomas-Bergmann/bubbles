package de.hatoka.bubbles.human.internal.remote;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.remote.HumanDataRO;
import de.hatoka.bubbles.human.capi.remote.HumanInfoRO;
import de.hatoka.bubbles.human.capi.remote.HumanRO;

@Component
public class HumanBO2RO
{
    public HumanRO apply(HumanBO human)
    {
        HumanInfoRO info = new HumanInfoRO();
        info.setAbbreviation(human.getRef().getAbbreviation());
        HumanDataRO data = new HumanDataRO();
        data.setName(human.getName());

        HumanRO result = new HumanRO();
        result.setRefGlobal(human.getRef().getGlobalRef());
        result.setRefLocal(human.getRef().getLocalRef());
        result.setResourceURI("/bubbles/" + human.getRef().getLocalRef());
        result.setData(data);
        result.setInfo(info);
        return result;
    }

    public List<HumanRO> apply(Collection<HumanBO> projects)
    {
        return projects.stream().map(this::apply).collect(Collectors.toList());
    }
}
