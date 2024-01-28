package de.hatoka.bubbles.bubble.internal.business;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleBORepository;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.internal.persistence.BubbleDao;
import de.hatoka.bubbles.bubble.internal.persistence.BubblePO;
import de.hatoka.user.capi.business.UserRef;

@Component
public class BubbleBORepositoryImpl implements BubbleBORepository
{
    @Autowired
    private BubbleDao bubbleDao;
    @Autowired
    private BubbleBOFactory bubbleBOFactory;

    @Override
    public BubbleBO createBubble(BubbleRef bubbleRef, String name, UserRef userRef)
    {
        BubblePO po = new BubblePO();
        po.setUserRef(userRef.getGlobalRef());
        po.setExternalID(bubbleRef.getExternalID());
        po.setName(name);
        bubbleDao.save(po);
        return getBubble(bubbleRef);
    }

    @Override
    public BubbleBO getBubble(BubbleRef bubbleRef)
    {
        return bubbleBOFactory.get(bubbleRef);
    }

    @Override
    public Optional<BubbleBO> findBubble(BubbleRef bubbleRef)
    {
        LoggerFactory.getLogger(getClass()).trace("get bubblepo by abbreviation {}", bubbleRef.getGlobalRef());
        return bubbleDao.findByExternalID(bubbleRef.getExternalID()).map(bubbleBOFactory::get);
    }

    @Override
    public Collection<BubbleBO> getAllBubbles()
    {
        LoggerFactory.getLogger(getClass()).debug("get bubblepo by all");
        return bubbleDao.findAll().stream().map(bubbleBOFactory::get).toList();
    }

    @Override
    public Optional<BubbleBO> findBubble(Long internalBubbleId)
    {
        LoggerFactory.getLogger(getClass()).debug("get bubblepo by id {}", internalBubbleId);
        return bubbleDao.findById(internalBubbleId).map(bubbleBOFactory::get);
    }

    @Override
    public Collection<BubbleBO> getBubbles(UserRef userRef)
    {
        LoggerFactory.getLogger(getClass()).trace("get bubblepo by userRef {}", userRef.getGlobalRef());
        return bubbleDao.findByUserref(userRef.getGlobalRef()).stream().map(bubbleBOFactory::get).toList();
    }
}
