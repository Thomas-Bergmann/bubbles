package de.hatoka.bubbles.human.internal.business;

import java.util.Objects;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.internal.persistence.HumanDao;
import de.hatoka.bubbles.human.internal.persistence.HumanPO;
import de.hatoka.user.capi.business.UserRef;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HumanBOImpl implements HumanBO
{
    @Autowired
    private HumanDao humanDao;
    private final HumanRef humanRef;

    public HumanBOImpl(HumanRef humanRef)
    {
        this.humanRef = humanRef;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(humanRef);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HumanBOImpl other = (HumanBOImpl)obj;
        return Objects.equals(humanRef, other.humanRef);
    }

    @Override
    public String getName()
    {
        return getPO().getName();
    }

    @Override
    public void remove()
    {
        HumanPO po = getPO();
        humanDao.delete(po);
    }

    @SuppressWarnings("unused")
	private void savePO(HumanPO humanPO)
    {
    	humanDao.save(humanPO);
    }

    @Override
    public HumanRef getRef()
    {
        return humanRef;
    }

    private synchronized HumanPO getPO()
    {
        LoggerFactory.getLogger(getClass()).trace("get humanpo by name {}", humanRef.getGlobalRef());
        return humanDao.findByExternalID(humanRef.getExternalID()).get();
    }

    @Override
    public Long getInternalId()
    {
        return getPO().getId();
    }

    @Override
    public UserRef getUserRef()
    {
        return UserRef.globalRef(getPO().getUserRef());
    }
}
