package de.hatoka.bubbles.human.internal.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import de.hatoka.bubbles.human.capi.business.Gender;
import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.capi.remote.HumanCreateRO;
import de.hatoka.bubbles.human.capi.remote.HumanDataRO;
import de.hatoka.bubbles.human.capi.remote.HumanRO;
import de.hatoka.common.capi.value.IncompleteDate;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.human.HumanTestApplication;
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { HumanTestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
@ActiveProfiles("test")
public class HumanControllerTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF1 = HumanRef.localRef("name-1");
    private static final HumanRef HUMAN_REF2 = HumanRef.localRef("name-2");
    private static final IncompleteDate DATE_OF_BIRTH = IncompleteDate.valueOf("2011/10/07");
    private static final IncompleteDate DATE_OF_DEATH = IncompleteDate.valueOf("2023/11/dd");
    private static final IncompleteDate DATE_OF_DEATH_COMPLETE = IncompleteDate.valueOf("2023/11/07");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HumanBORepository repository;

    @BeforeEach @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void createHumanAndDelete()
    {
        HumanCreateRO data = new HumanCreateRO();
        data.setName(HUMAN_REF1.getExternalID());
        data.setUserRef(USER_REF_ONE.getLocalRef());
        data.setDateOfBirth(DATE_OF_BIRTH);
        data.setDateOfDeath(DATE_OF_DEATH);
        data.setGender(Gender.DIVERSE);
        putHuman(HUMAN_REF1, data);

        HumanRO ro = getHuman(HUMAN_REF1);
        assertNotNull(ro, "human created and found");
        assertNotNull(ro.getData(), "human contains data");
        assertEquals("name-1", ro.getData().getName());
        assertEquals(DATE_OF_BIRTH, ro.getData().getDateOfBirth());
        assertEquals(DATE_OF_DEATH, ro.getData().getDateOfDeath());
        assertEquals(Gender.DIVERSE, ro.getData().getGender());

        assertNotNull(ro.getInfo(), "human contains info");
        assertEquals(12, ro.getInfo().getAge());
        
        data.setDateOfDeath(DATE_OF_DEATH_COMPLETE);
        data.setGender(Gender.FEMALE);
        patchHuman(HUMAN_REF1, data);

        HumanRO afterUpdate = getHuman(HUMAN_REF1);
        assertNotNull(afterUpdate, "human created and found");
        assertNotNull(afterUpdate.getData(), "human contains data");
        assertEquals(DATE_OF_DEATH_COMPLETE, afterUpdate.getData().getDateOfDeath());
        assertEquals(Gender.FEMALE, afterUpdate.getData().getGender());

        deleteHuman(HUMAN_REF1);
    }

    @Test
    public void testGetHumans()
    {
        HumanCreateRO data = new HumanCreateRO();
        data.setUserRef(USER_REF_ONE.getLocalRef());
        data.setName(HUMAN_REF1.getExternalID());
        putHuman(HUMAN_REF1, data);
        data.setName(HUMAN_REF2.getExternalID());
        putHuman(HUMAN_REF2, data);

        List<HumanRO> humans = getList(USER_REF_ONE);
        assertEquals(2, humans.size());
        deleteHuman(HUMAN_REF1);
        deleteHuman(HUMAN_REF2);
    }

    @Test
    public void testGetParents()
    {
        HumanCreateRO data = new HumanCreateRO();
        data.setUserRef(USER_REF_ONE.getLocalRef());
        data.setName(HUMAN_REF1.getExternalID());
        putHuman(HUMAN_REF1, data);
        data.setName(HUMAN_REF2.getExternalID());
        putHuman(HUMAN_REF2, data);
        // before having a child
        assertEquals(0, getParents(HUMAN_REF2).size());
        // make a child
        HumanBO dad = repository.getHuman(HUMAN_REF1);
        HumanBO child = repository.getHuman(HUMAN_REF2);
        dad.addChild(child);
        // test the child query
        List<HumanRO> parents = getParents(HUMAN_REF2);
        assertEquals(1, parents.size());
        assertEquals(HUMAN_REF1.getGlobalRef(), parents.get(0).getRefGlobal());
        deleteHuman(HUMAN_REF1);
        deleteHuman(HUMAN_REF2);
    }

    private List<HumanRO> getParents(HumanRef childRef)
    {
        String uri = HumanController.PATH_ROOT + "?" + HumanController.QUERY_CHILD_REF + "=" + childRef.getLocalRef();
        return Arrays.asList(this.restTemplate.getForObject(uri, HumanRO[].class));
    }

    private List<HumanRO> getList(UserRef userRef)
    {
        String uri = HumanController.PATH_ROOT + "?" + HumanController.QUERY_USER_REF + "=" + userRef.getLocalRef();
        return Arrays.asList(this.restTemplate.getForObject(uri, HumanRO[].class));
    }

    private Map<String, String> createURIParameter(HumanRef ref)
    {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(HumanController.PATH_VAR_HUMANID, ref.getExternalID());
        return urlParams;
    }

    private HumanRO getHuman(HumanRef ref)
    {
        return this.restTemplate.getForObject(HumanController.PATH_HUMAN, HumanRO.class, createURIParameter(ref));
    }

    private void putHuman(HumanRef ref, HumanCreateRO data)
    {
        HttpEntity<HumanCreateRO> entity = new HttpEntity<>(data);
        ResponseEntity<Void> response = this.restTemplate.exchange(HumanController.PATH_HUMAN, HttpMethod.PUT, entity , Void.class, createURIParameter(ref));
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void patchHuman(HumanRef ref, HumanDataRO data)
    {
        HttpEntity<HumanDataRO> entity = new HttpEntity<>(data);
        ResponseEntity<Void> response = this.restTemplate.exchange(HumanController.PATH_HUMAN, HttpMethod.PATCH, entity , Void.class, createURIParameter(ref));
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void deleteHuman(HumanRef ref)
    {
        this.restTemplate.delete(HumanController.PATH_HUMAN, createURIParameter(ref));
    }
}
