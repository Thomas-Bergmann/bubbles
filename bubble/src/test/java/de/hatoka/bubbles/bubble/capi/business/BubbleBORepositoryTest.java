package de.hatoka.bubbles.bubble.capi.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

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
public class BubbleBORepositoryTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF_ONE = BubbleRef.localRef("bubble-one");
    private static final BubbleRef BUBBLE_REF_TWO = BubbleRef.localRef("bubble-two");

    @Autowired
    private BubbleBORepository repository;

    @BeforeEach @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void testCrud()
    {
        BubbleBO project1 = repository.createBubble(BUBBLE_REF_ONE, BUBBLE_REF_ONE.getAbbreviation(), USER_REF_ONE);
        BubbleBO project2 = repository.createBubble(BUBBLE_REF_TWO, BUBBLE_REF_TWO.getAbbreviation(), USER_REF_ONE);
        Collection<BubbleBO> projects = repository.getAllBubbles();
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
        project1.remove();
        projects = repository.getAllBubbles();
        assertEquals(1, projects.size());
        project2.remove();
        projects = repository.getAllBubbles();
        assertTrue(projects.isEmpty());
    }

}
