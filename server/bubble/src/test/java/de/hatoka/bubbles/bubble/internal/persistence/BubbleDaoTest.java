package de.hatoka.bubbles.bubble.internal.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.bubble.BubbleTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BubbleTestConfiguration.class })
public class BubbleDaoTest
{
    private static final String NAME = "BubbleDaoTest-N";
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF_ONE = BubbleRef.localRef(NAME);

    @Autowired
    private BubbleDao dao;

    @Test
    public void testCRUD()
    {
        BubblePO BubblePO = newBubblePO(USER_REF_ONE, NAME);
        dao.save(BubblePO);
        Optional<BubblePO> findBubblePO= dao.findByExternalID(BUBBLE_REF_ONE.getExternalID());
        assertEquals(BubblePO, findBubblePO.get());
        dao.delete(BubblePO);
    }

    private BubblePO newBubblePO(UserRef userRef, String name)
    {
        BubblePO result = new BubblePO();
        result.setUserRef(userRef.getGlobalRef());
        result.setExternalID(name);
        result.setName(name);
        return result;
    }
}
