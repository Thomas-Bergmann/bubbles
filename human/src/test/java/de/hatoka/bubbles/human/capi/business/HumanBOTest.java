package de.hatoka.bubbles.human.capi.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
public class HumanBOTest
{
    private static final String NAME = "HumanBOTest";
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF_ONE = HumanRef.localRef(NAME);

    @Autowired
    private HumanBORepository humanRepo;

    @BeforeEach
    public void createPlayer()
    {
        deleteRepo();
    }

     @AfterEach
    public void deleteRepo()
    {
        humanRepo.clear();
    }

    @Test
    public void testCRUD() throws IOException
    {
        HumanBO human = humanRepo.createHuman(HUMAN_REF_ONE, HUMAN_REF_ONE.getExternalID(), USER_REF_ONE);
        assertEquals(NAME, human.getName());
        assertEquals("human:HumanBOTest", human.getRef().getGlobalRef());
    }
}
