package de.hatoka.bubbles.bubble.internal.business;

import java.util.Objects;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.internal.persistence.BubbleDao;
import de.hatoka.bubbles.bubble.internal.persistence.BubblePO;
import de.hatoka.user.capi.business.UserRef;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BubbleBOImpl implements BubbleBO
{
    @Autowired
    private BubbleDao bubbleDao;
    private final BubbleRef bubbleRef;

    public BubbleBOImpl(BubbleRef bubbleRef)
    {
        this.bubbleRef = bubbleRef;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(bubbleRef);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BubbleBOImpl other = (BubbleBOImpl)obj;
        return Objects.equals(bubbleRef, other.bubbleRef);
    }

    @Override
    public String getName()
    {
        return getPO().getName();
    }

    @Override
    public void remove()
    {
        BubblePO po = getPO();
        bubbleDao.delete(po);
    }

    @SuppressWarnings("unused")
	private void savePO(BubblePO bubblePO)
    {
    	bubbleDao.save(bubblePO);
    }

    @Override
    public BubbleRef getRef()
    {
        return bubbleRef;
    }

    private synchronized BubblePO getPO()
    {
        LoggerFactory.getLogger(getClass()).trace("get bubblepo by name {}", bubbleRef.getGlobalRef());
        return bubbleDao.findByExternalID(bubbleRef.getExternalID()).get();
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
