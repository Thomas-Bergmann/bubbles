package de.hatoka.bubbles.human.capi.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

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
    private static final HumanRef HUMAN_REF_ONE = HumanRef.localRef(NAME + "-1");
    private static final HumanRef HUMAN_REF_TWO = HumanRef.localRef(NAME + "-2");

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
        HumanBO human = humanRepo.createHuman(HUMAN_REF_ONE, NAME, USER_REF_ONE);
        assertEquals(NAME, human.getName());
        assertEquals("human:HumanBOTest-1", human.getRef().getGlobalRef());
    }

    @Test
    public void testChildrenRelation()
    {
        HumanBO child = humanRepo.createHuman(HUMAN_REF_ONE, HUMAN_REF_ONE.getExternalID(), USER_REF_ONE);
        HumanBO parent = humanRepo.createHuman(HUMAN_REF_TWO, HUMAN_REF_TWO.getExternalID(), USER_REF_ONE);
        parent.addChild(child);
        // check children
        List<HumanBO> children = parent.getChildren();
        assertEquals(1, children.size());
        assertEquals(child, children.get(0));
        // check parent
        List<HumanBO> parents = child.getParents();
        assertEquals(1, parents.size());
        assertEquals(parent, parents.get(0));
        
        parent.removeChild(child);
        assertTrue(parent.getChildren().isEmpty(), "children are removed");
        assertTrue(child.getParents().isEmpty(), "parents are removed");
    }

    @Test
    public void testMariageRelation()
    {
        HumanBO wife = humanRepo.createHuman(HUMAN_REF_ONE, HUMAN_REF_ONE.getExternalID(), USER_REF_ONE);
        HumanBO husband = humanRepo.createHuman(HUMAN_REF_TWO, HUMAN_REF_TWO.getExternalID(), USER_REF_ONE);
        wife.addMariageWith(husband);
        // check both sides
        List<HumanBO> wifePartners = wife.getMariageWith();
        assertEquals(1, wifePartners.size());
        assertEquals(husband, wifePartners.get(0));
        // check parent
        List<HumanBO> husbandPartners = husband.getMariageWith();
        assertEquals(1, husbandPartners.size());
        assertEquals(wife, husbandPartners.get(0));
        
        wife.removeMariageWith(husband);
        assertTrue(wife.getMariageWith().isEmpty(), "husband is removed");
        assertTrue(husband.getMariageWith().isEmpty(), "wife is removed");
    }
}
