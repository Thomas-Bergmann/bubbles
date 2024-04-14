package de.hatoka.bubbles.bubble.internal.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hatoka.bubbles.bubble.capi.business.BubbleBORepository;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.capi.remote.BubbleCreateRO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleRO;
import de.hatoka.user.capi.business.UserRef;
import tests.de.hatoka.bubbles.bubble.BubbleTestApplication;
import tests.de.hatoka.bubbles.bubble.BubbleTestConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { BubbleTestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { BubbleTestConfiguration.class })
@ActiveProfiles("test")
public class BubbleControllerTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF1 = BubbleRef.localRef("name-1");
    private static final BubbleRef BUBBLE_REF2 = BubbleRef.localRef("name-2");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BubbleBORepository repository;

    @BeforeEach @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void createBubbleAndDelete()
    {
        BubbleCreateRO data = new BubbleCreateRO();
        data.setName(BUBBLE_REF1.getExternalID());
        data.setUserRef(USER_REF_ONE.getLocalRef());
        putBubble(BUBBLE_REF1, data);

        BubbleRO ro = getBubble(BUBBLE_REF1);
        assertNotNull(ro, "bubble created and found");
        assertNotNull(ro.getData(), "bubble contains data");
        assertEquals("name-1", ro.getData().getName());
        deleteBubble(BUBBLE_REF1);
    }

    @Test
    public void testGetBubbles()
    {
        BubbleCreateRO data = new BubbleCreateRO();
        data.setUserRef(USER_REF_ONE.getLocalRef());
        data.setName(BUBBLE_REF1.getExternalID());
        putBubble(BUBBLE_REF1, data);
        data.setName(BUBBLE_REF2.getExternalID());
        putBubble(BUBBLE_REF2, data);

        List<BubbleRO> bubbles = getBubbles(USER_REF_ONE);
        assertEquals(2, bubbles.size());
        deleteBubble(BUBBLE_REF1);
        deleteBubble(BUBBLE_REF2);
    }

    private List<BubbleRO> getBubbles(UserRef userRef)
    {
        String uri = BubbleController.PATH_ROOT + "?" + BubbleController.QUERY_USER_REF + "={userRef}";
        ResponseEntity<List<BubbleRO>> response = this.restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<BubbleRO>>() {}, userRef.getLocalRef());
        return response.getBody();
    }

    private Map<String, String> createURIParameter(BubbleRef ref)
    {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(BubbleController.PATH_VAR_BUBBLEID, ref.getExternalID());
        return urlParams;
    }

    private BubbleRO getBubble(BubbleRef ref)
    {
        return this.restTemplate.getForObject(BubbleController.PATH_BUBBLE, BubbleRO.class, createURIParameter(ref));
    }

    private void putBubble(BubbleRef ref, BubbleCreateRO data)
    {
        HttpEntity<BubbleCreateRO> entity = new HttpEntity<>(data);
        ResponseEntity<Void> response = this.restTemplate.exchange(BubbleController.PATH_BUBBLE, HttpMethod.PUT, entity , Void.class, createURIParameter(ref));
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void deleteBubble(BubbleRef ref)
    {
        this.restTemplate.delete(BubbleController.PATH_BUBBLE, createURIParameter(ref));
    }
}
