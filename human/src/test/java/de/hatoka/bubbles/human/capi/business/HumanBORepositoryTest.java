package de.hatoka.bubbles.human.capi.business;

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
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
public class HumanBORepositoryTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF_ONE = HumanRef.localRef("human-one");
    private static final HumanRef HUMAN_REF_TWO = HumanRef.localRef("human-two");

    @Autowired
    private HumanBORepository repository;

    @BeforeEach @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void testCrud()
    {
        HumanBO project1 = repository.createHuman(HUMAN_REF_ONE, HUMAN_REF_ONE.getAbbreviation(), USER_REF_ONE);
        HumanBO project2 = repository.createHuman(HUMAN_REF_TWO, HUMAN_REF_TWO.getAbbreviation(), USER_REF_ONE);
        Collection<HumanBO> projects = repository.getAllHumans();
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
        project1.remove();
        projects = repository.getAllHumans();
        assertEquals(1, projects.size());
        project2.remove();
        projects = repository.getAllHumans();
        assertTrue(projects.isEmpty());
    }

}
