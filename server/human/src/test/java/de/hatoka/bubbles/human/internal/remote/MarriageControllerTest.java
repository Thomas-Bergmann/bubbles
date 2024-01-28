package de.hatoka.bubbles.human.internal.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
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
import de.hatoka.bubbles.human.capi.remote.MarriageDataRO;
import de.hatoka.common.capi.value.IncompleteDate;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.human.HumanTestApplication;
import tests.de.hatoka.bubbles.human.HumanTestConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { HumanTestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { HumanTestConfiguration.class })
@ActiveProfiles("test")
public class MarriageControllerTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final HumanRef HUMAN_REF_MOM = HumanRef.localRef("mom");
    private static final HumanRef HUMAN_REF_DAD = HumanRef.localRef("dad");

    private static final IncompleteDate DATE_OF_START = IncompleteDate.valueOf("2011/10/07");
    private static final IncompleteDate DATE_OF_END = IncompleteDate.valueOf("2023/11/12");

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
    }

    @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void crudMarriage()
    {
        putMarriage(HUMAN_REF_MOM, HUMAN_REF_DAD);

        List<MarriageDataRO> momsRelations = getRelations(HUMAN_REF_MOM);
        List<MarriageDataRO> dadsRelations = getRelations(HUMAN_REF_DAD);
        assertEquals(1, momsRelations.size());
        assertEquals(DATE_OF_START, IncompleteDate.valueOf(momsRelations.get(0).getDateOfStart()));
        assertEquals(DATE_OF_END, IncompleteDate.valueOf(momsRelations.get(0).getDateOfEnd()));
        assertEquals(1, dadsRelations.size());
        assertEquals(momsRelations, dadsRelations);

        removeMarriage(HUMAN_REF_DAD, HUMAN_REF_MOM);
        assertEquals(0, getRelations(HUMAN_REF_MOM).size());
        assertEquals(0, getRelations(HUMAN_REF_DAD).size());
    }

    private List<MarriageDataRO> getRelations(HumanRef humanRef)
    {
        URI uri = URI.create(MarriageController.PATH_ROOT + "?" + MarriageController.PARAM_HUMAN_REF + "=" + humanRef.getLocalRef());
        return Arrays.asList(this.restTemplate.getForObject(uri, MarriageDataRO[].class));
    }

    private void putMarriage(HumanRef momRef, HumanRef dadRef)
    {
        String uri = MarriageController.PATH_ROOT + MarriageController.SUB_PATH_CREATE;
        MarriageDataRO data = new MarriageDataRO();
        data.setPartner1LocalRef(momRef.getLocalRef());
        data.setPartner2LocalRef(dadRef.getLocalRef());
        data.setDateOfStart(DATE_OF_START.toString());
        data.setDateOfEnd(DATE_OF_END.toString());
        HttpEntity<MarriageDataRO> entity = new HttpEntity<>(data);
        ResponseEntity<Void> response = this.restTemplate.exchange(uri, HttpMethod.POST, entity, Void.class);
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void removeMarriage(HumanRef momRef, HumanRef dadRef)
    {
        String uri = MarriageController.PATH_ROOT + MarriageController.SUB_PATH_REMOVE;
        MarriageDataRO data = new MarriageDataRO();
        data.setPartner1LocalRef(momRef.getLocalRef());
        data.setPartner2LocalRef(dadRef.getLocalRef());
        HttpEntity<MarriageDataRO> entity = new HttpEntity<>(data);
        ResponseEntity<Void> response = this.restTemplate.exchange(uri, HttpMethod.POST, entity, Void.class);
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }
}
