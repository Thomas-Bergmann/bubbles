package de.hatoka.bubbles.bubble.internal.business;

import de.hatoka.bubbles.bubble.capi.business.Membership;
import de.hatoka.bubbles.bubble.internal.persistence.BubbleHumanDao;
import de.hatoka.bubbles.bubble.internal.persistence.BubbleHumanPO;
import de.hatoka.bubbles.human.capi.business.HumanRef;

public class PartOfBubbleImpl implements Membership
{
    private final BubbleHumanDao bubbleHumanDao;
    private final BubbleHumanPO humanRel;

    PartOfBubbleImpl(BubbleHumanPO humanRel, BubbleHumanDao bubbleHumanDao)
    {
        this.humanRel = humanRel;
        this.bubbleHumanDao = bubbleHumanDao;
    }

    @Override
    public HumanRef getHumanRef()
    {
        return HumanRef.globalRef(humanRel.getHumanRef());
    }

    @Override
    public boolean isStillPartOf()
    {
        return humanRel.isStillPart();
    }

    @Override
    public Membership setActive(boolean active)
    {
        humanRel.setStillPart(active);
        BubbleHumanPO newHumanRel = bubbleHumanDao.save(humanRel);
        return new PartOfBubbleImpl(newHumanRel, bubbleHumanDao);
    }
}
