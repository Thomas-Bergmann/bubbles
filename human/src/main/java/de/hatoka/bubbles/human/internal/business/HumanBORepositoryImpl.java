package de.hatoka.bubbles.human.internal.business;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.internal.persistence.HumanDao;
import de.hatoka.bubbles.human.internal.persistence.HumanPO;
import de.hatoka.user.capi.business.UserRef;

@Component
public class HumanBORepositoryImpl implements HumanBORepository
{
    @Autowired
    private HumanDao humanDao;
    @Autowired
    private HumanBOFactory humanBOFactory;

    @Override
    public HumanBO createHuman(HumanRef humanRef, String name, UserRef userRef)
    {
        HumanPO po = new HumanPO();
        po.setUserRef(userRef.getGlobalRef());
        po.setExternalID(humanRef.getExternalID());
        po.setName(name);
        humanDao.save(po);
        return getHuman(humanRef);
    }

    @Override
    public HumanBO getHuman(HumanRef humanRef)
    {
        return humanBOFactory.get(humanRef);
    }

    @Override
    public Optional<HumanBO> findHuman(HumanRef humanRef)
    {
        LoggerFactory.getLogger(getClass()).trace("get humanpo by abbreviation {}", humanRef.getGlobalRef());
        return humanDao.findByExternalID(humanRef.getExternalID()).map(humanBOFactory::get);
    }

    @Override
    public Collection<HumanBO> getAllHumans()
    {
        LoggerFactory.getLogger(getClass()).debug("get humanpo by all");
        return humanDao.findAll().stream().map(humanBOFactory::get).toList();
    }

    @Override
    public Optional<HumanBO> findHuman(Long internalHumanId)
    {
        LoggerFactory.getLogger(getClass()).debug("get humanpo by id {}", internalHumanId);
        return humanDao.findById(internalHumanId).map(humanBOFactory::get);
    }

    @Override
    public Collection<HumanBO> getHumans(UserRef userRef)
    {
        LoggerFactory.getLogger(getClass()).trace("get humanpo by userRef {}", userRef.getGlobalRef());
        return humanDao.findByUserref(userRef.getGlobalRef()).stream().map(humanBOFactory::get).toList();
    }
}
