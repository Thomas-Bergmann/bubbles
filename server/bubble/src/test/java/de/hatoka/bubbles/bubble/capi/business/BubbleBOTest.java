package de.hatoka.bubbles.bubble.capi.business;

import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.user.capi.business.UserRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tests.de.hatoka.bubbles.bubble.BubbleTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BubbleTestConfiguration.class })
public class BubbleBOTest
{
    private static final String NAME = "BubbleBOTest";
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF_ONE = BubbleRef.localRef(NAME);
    private static final HumanRef MEMBER_1 = HumanRef.localRef("member-one");
    private static final HumanRef MEMBER_2 = HumanRef.localRef("member-two");

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
    public void testCRUD()
    {
        BubbleBO bubble = bubbleRepo.createBubble(BUBBLE_REF_ONE, BUBBLE_REF_ONE.getExternalID(), USER_REF_ONE);
        assertEquals(NAME, bubble.getName());
        assertEquals("bubble:BubbleBOTest", bubble.getRef().getGlobalRef());
    }
    @Test
    public void testMembers()
    {
        BubbleBO bubble = bubbleRepo.createBubble(BUBBLE_REF_ONE, BUBBLE_REF_ONE.getExternalID(), USER_REF_ONE);
        bubble.add(MEMBER_1);
        assertEquals(1, bubble.getMembers().size());
        assertTrue(bubble.exists(MEMBER_1));
        bubble.add(MEMBER_2);
        assertEquals(2, bubble.getMembers().size());
        bubble.add(MEMBER_1);
        assertEquals(2, bubble.getMembers().size());
        bubble.remove(MEMBER_1);
        assertEquals(1, bubble.getMembers().size());
    }
}
