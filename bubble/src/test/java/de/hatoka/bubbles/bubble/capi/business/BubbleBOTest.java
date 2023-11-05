package de.hatoka.bubbles.bubble.capi.business;

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
import tests.de.hatoka.bubbles.bubble.BubbleTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BubbleTestConfiguration.class })
public class BubbleBOTest
{
    private static final String NAME = "BubbleBOTest";
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF_ONE = BubbleRef.localRef(NAME);

    @Autowired
    private BubbleBORepository bubbleRepo;

    @BeforeEach
    public void createPlayer()
    {
        deleteRepo();
    }

     @AfterEach
    public void deleteRepo()
    {
        bubbleRepo.clear();
    }

    @Test
    public void testCRUD() throws IOException
    {
        BubbleBO bubble = bubbleRepo.createBubble(BUBBLE_REF_ONE, BUBBLE_REF_ONE.getAbbreviation(), USER_REF_ONE);
        assertEquals(NAME, bubble.getName());
        assertEquals("bubble:BubbleBOTest", bubble.getRef().getGlobalRef());
    }
}
