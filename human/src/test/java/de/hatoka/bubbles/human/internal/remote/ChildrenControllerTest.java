package de.hatoka.bubbles.human.internal.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.capi.remote.ChildCreateRO;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.human.HumanTestApplication;
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { HumanTestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
@ActiveProfiles("test")
public class ChildrenControllerTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF_MOM = HumanRef.localRef("mom");
    private static final HumanRef HUMAN_REF_DAD = HumanRef.localRef("dad");
    private static final HumanRef HUMAN_REF_CHILD = HumanRef.localRef("child");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HumanBORepository repository;

    @BeforeEach
    public void fillRepo()
    {
        deleteRepo();
        repository.createHuman(HUMAN_REF_MOM, "mom", USER_REF_ONE);
        repository.createHuman(HUMAN_REF_DAD, "dad", USER_REF_ONE);
        repository.createHuman(HUMAN_REF_CHILD, "child", USER_REF_ONE);
    }

    @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void crudChildrenRelations()
    {
        putChild(HUMAN_REF_MOM, HUMAN_REF_CHILD);
        putChild(HUMAN_REF_DAD, HUMAN_REF_CHILD);

        List<String> childrenOfMom = getChildren(HUMAN_REF_MOM);
        assertEquals(1, childrenOfMom.size());
        assertEquals(HUMAN_REF_CHILD.getLocalRef(), childrenOfMom.get(0));

        List<String> childrenOfDad = getChildren(HUMAN_REF_DAD);
        assertEquals(1, childrenOfDad.size());
        assertEquals(HUMAN_REF_CHILD.getLocalRef(), childrenOfDad.get(0));

        List<String> parents = getParents(HUMAN_REF_CHILD);
        assertEquals(2, parents.size());
        assertTrue(parents.contains(HUMAN_REF_MOM.getLocalRef()));
        assertTrue(parents.contains(HUMAN_REF_DAD.getLocalRef()));

        deleteChild(HUMAN_REF_MOM, HUMAN_REF_CHILD);
        deleteChild(HUMAN_REF_DAD, HUMAN_REF_CHILD);
    }

    private List<String> getParents(HumanRef childRef)
    {
        String uri = ChildrenController.PATH_ROOT + ChildrenController.PATH_SUB_PARENTS;
        return Arrays.asList(this.restTemplate.getForObject(uri, String[].class, childRef.getLocalRef()));
    }

    private List<String> getChildren(HumanRef parentRef)
    {
        String uri = ChildrenController.PATH_ROOT + ChildrenController.PATH_SUB_CHILDREN;
        return Arrays.asList(this.restTemplate.getForObject(uri, String[].class, parentRef.getLocalRef()));
    }

    private void putChild(HumanRef parentRef, HumanRef childRef)
    {
        String uri = ChildrenController.PATH_ROOT + ChildrenController.PATH_SUB_CHILD;
        HttpEntity<ChildCreateRO> entity = new HttpEntity<>(new ChildCreateRO());
        ResponseEntity<Void> response = this.restTemplate.exchange(uri, HttpMethod.PUT, entity, Void.class, parentRef.getLocalRef(), childRef.getLocalRef());
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void deleteChild(HumanRef parentRef, HumanRef childRef)
    {
        String uri = ChildrenController.PATH_ROOT + ChildrenController.PATH_SUB_CHILD;
        this.restTemplate.delete(uri, parentRef.getLocalRef(), childRef.getLocalRef());
    }
}
