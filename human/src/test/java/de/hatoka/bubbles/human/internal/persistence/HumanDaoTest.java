package de.hatoka.bubbles.human.internal.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
public class HumanDaoTest
{
    private static final String NAME = "HumanDaoTest-N";
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF_ONE = HumanRef.localRef(NAME);

    @Autowired
    private HumanDao dao;

    @Test
    public void testCRUD()
    {
        HumanPO HumanPO = newHumanPO(USER_REF_ONE, NAME);
        dao.save(HumanPO);
        Optional<HumanPO> findHumanPO= dao.findByExternalID(HUMAN_REF_ONE.getExternalID());
        assertEquals(HumanPO, findHumanPO.get());
        dao.delete(HumanPO);
    }

    private HumanPO newHumanPO(UserRef userRef, String name)
    {
        HumanPO result = new HumanPO();
        result.setUserRef(userRef.getGlobalRef());
        result.setExternalID(name);
        result.setName(name);
        return result;
    }
}
