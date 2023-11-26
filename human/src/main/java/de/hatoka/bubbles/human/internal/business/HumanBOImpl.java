package de.hatoka.bubbles.human.internal.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.hatoka.bubbles.human.capi.business.FlexRelation;
import de.hatoka.bubbles.human.capi.business.Gender;
import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.capi.business.RelationType;
import de.hatoka.bubbles.human.internal.persistence.HumanDao;
import de.hatoka.bubbles.human.internal.persistence.HumanPO;
import de.hatoka.bubbles.human.internal.persistence.HumanRelationFixDao;
import de.hatoka.bubbles.human.internal.persistence.HumanRelationFixPO;
import de.hatoka.bubbles.human.internal.persistence.HumanRelationFlexDao;
import de.hatoka.bubbles.human.internal.persistence.HumanRelationFlexPO;
import de.hatoka.common.capi.value.IncompleteDate;
import de.hatoka.user.capi.business.UserRef;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HumanBOImpl implements HumanBO
{
    @Autowired
    private HumanDao humanDao;
    @Autowired
    private HumanRelationFixDao fixRelationDao;
    @Autowired
    private HumanRelationFlexDao flexRelationDao;
    @Autowired
    private HumanBORepository repository;

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

    @Override
    public void setDateOfBirth(IncompleteDate dateOfBirth)
    {
        HumanPO po = getPO();
        po.setDateOfBirth(dateOfBirth.toString());
        savePO(po);
    }

    @Override
    public IncompleteDate getDateOfBirth()
    {
        return IncompleteDate.valueOf(getPO().getDateOfBirth());
    }

    @Override
    public void setDateOfDeath(IncompleteDate dateOfDeath)
    {
        HumanPO po = getPO();
        po.setDateOfDeath(dateOfDeath.toString());
        savePO(po);
    }

    @Override
    public IncompleteDate getDateOfDeath()
    {
        return IncompleteDate.valueOf(getPO().getDateOfDeath());
    }

    @Override
    public void setGender(Gender gender)
    {
        HumanPO po = getPO();
        po.setGender(gender);
        savePO(po);
    }

    @Override
    public Gender getGender()
    {
        return getPO().getGender();
    }

    @Override
    public void addFixRelation(RelationType type, HumanBO other)
    {
        if (!type.isBiDirectional() && type.isReverse())
        {
            other.addFixRelation(type.getReverseRelation(), this);
            return;
        }
        HumanRelationFixPO relation = new HumanRelationFixPO();
        relation.setHuman1(this.getInternalId());
        relation.setHuman2(other.getInternalId());
        relation.setType(type);
        relation = fixRelationDao.save(relation);
    }

    @Override
    public List<HumanBO> getFixRelations(RelationType type)
    {
        if (type.isBiDirectional())
        {
            return getBiDirectionalRelations(type);
        }
        if (type.isReverse())
        {
            return getReverseRelations(type);
        }
        return getNonReverseRelations(type);
    }

    private List<HumanBO> getNonReverseRelations(RelationType type)
    {
        return map(fixRelationDao.findByHuman1(getInternalId()).stream().filter(po -> type.equals(po.getType())).map(HumanRelationFixPO::getHuman2));
    }

    private List<HumanBO> getReverseRelations(RelationType type)
    {
        final RelationType reverseType = type.getReverseRelation();
        return map(fixRelationDao.findByHuman2(getInternalId()).stream().filter(po -> reverseType.equals(po.getType())).map(HumanRelationFixPO::getHuman1));
    }

    private List<HumanBO> getBiDirectionalRelations(RelationType type)
    {
        List<Long> relations = new ArrayList<>();
        relations.addAll(fixRelationDao.findByHuman1(getInternalId()).stream().filter(po -> type.equals(po.getType())).map(HumanRelationFixPO::getHuman2).toList());
        relations.addAll(fixRelationDao.findByHuman2(getInternalId()).stream().filter(po -> type.equals(po.getType())).map(HumanRelationFixPO::getHuman1).toList());
        return map(relations.stream());
    }

    private List<HumanBO> map(Stream<Long> relations)
    {
        return relations.distinct().map(repository::findHuman).filter(Optional::isPresent).map(Optional::get).toList();
    }

    @Override
    public void removeFixRelation(RelationType type, HumanBO other)
    {
        final Long otherID = other.getInternalId();
        if (type.isBiDirectional() || !type.isReverse())
        {
            fixRelationDao.findByHuman1(getInternalId()).stream().filter(po -> type.equals(po.getType())).filter(po -> otherID.equals(po.getHuman2())).forEach(fixRelationDao::delete);
        }
        if (type.isBiDirectional() || type.isReverse())
        {
            final RelationType reverseType = type.getReverseRelation();
            fixRelationDao.findByHuman2(getInternalId()).stream().filter(po -> reverseType.equals(po.getType())).filter(po -> otherID.equals(po.getHuman1())).forEach(fixRelationDao::delete);
        }
    }

    @Override
    public void addMariageWith(HumanBO partner, IncompleteDate start, IncompleteDate end)
    {
        HumanRelationFlexPO relation = new HumanRelationFlexPO();
        relation.setHuman1(this.getInternalId());
        relation.setHuman2(partner.getInternalId());
        relation.setType(RelationType.MARRIED_WITH);
        relation.setDateStart(start.toString());
        relation.setDateEnd(end.toString());
        relation = flexRelationDao.save(relation);
    }

    @Override
    public void removeMariageWith(HumanBO partner)
    {
        Long otherID = partner.getInternalId();
        flexRelationDao.findByHuman1(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).filter(po -> otherID.equals(po.getHuman2())).forEach(flexRelationDao::delete);
        flexRelationDao.findByHuman2(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).filter(po -> otherID.equals(po.getHuman1())).forEach(flexRelationDao::delete);
    }

    @Override
    public List<HumanBO> getMariageWith()
    {
        List<Long> relations = new ArrayList<>();
        relations.addAll(flexRelationDao.findByHuman1(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).map(HumanRelationFlexPO::getHuman2).toList());
        relations.addAll(flexRelationDao.findByHuman2(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).map(HumanRelationFlexPO::getHuman1).toList());
        return map(relations.stream());
    }

    @Override
    public List<FlexRelation> getMariageRelations()
    {
        List<HumanRelationFlexPO> relations = new ArrayList<>();
        relations.addAll(flexRelationDao.findByHuman1(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).toList());
        relations.addAll(flexRelationDao.findByHuman2(getInternalId()).stream().filter(po -> RelationType.MARRIED_WITH.equals(po.getType())).toList());
        return relations.stream().map(this::map).toList();
    }

    private FlexRelation map(HumanRelationFlexPO flex)
    {
        return new FlexRelationImpl(flex, repository);
    }
}
