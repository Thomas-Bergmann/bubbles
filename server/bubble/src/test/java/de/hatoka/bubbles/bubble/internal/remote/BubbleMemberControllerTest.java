package de.hatoka.bubbles.bubble.internal.remote;

import de.hatoka.bubbles.bubble.capi.business.BubbleBORepository;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.capi.remote.BubbleHumanPartRO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleMemberCreateRO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleRO;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.user.capi.business.UserRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;
import tests.de.hatoka.bubbles.bubble.BubbleTestApplication;
import tests.de.hatoka.bubbles.bubble.BubbleTestConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { BubbleTestApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { BubbleTestConfiguration.class })
@ActiveProfiles("test")
public class BubbleMemberControllerTest
{
    private static final UserRef USER_REF_ONE = UserRef.localRef("user-one");
    private static final BubbleRef BUBBLE_REF1 = BubbleRef.localRef("bubble-1");
    private static final HumanRef MEMBER_REF1 = HumanRef.localRef("member-1");
    private static final HumanRef MEMBER_REF2 = HumanRef.localRef("member-2");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BubbleBORepository repository;

    @BeforeEach
    public void setup()
    {
        deleteRepo();
        repository.createBubble(BUBBLE_REF1, BUBBLE_REF1.getLocalRef(), USER_REF_ONE);
    }

    @AfterEach
    public void deleteRepo()
    {
        repository.clear();
    }

    @Test
    public void createMemberAndDelete()
    {
        BubbleRO ro = getBubble(BUBBLE_REF1);
        assertNotNull(ro, "bubble created and found");
        assertNotNull(ro.getData(), "bubble contains data");
        assertTrue(ro.getData().getHumans().isEmpty(), "bubble doesn't contain member");

        // add member one as active
        BubbleMemberCreateRO data = new BubbleMemberCreateRO();
        data.setStillPart(true);
        putMember(BUBBLE_REF1, MEMBER_REF1, data);
        BubbleRO roWithMember = getBubble(BUBBLE_REF1);
        assertEquals(1, roWithMember.getData().getHumans().size(), "bubble has member");
        assertTrue(roWithMember.getData().getHumans().getFirst().isStillPart(), "first member is active");

        // add member two as inactive
        data.setStillPart(false);
        putMember(BUBBLE_REF1, MEMBER_REF2, data);
        BubbleRO roWithTwo = getBubble(BUBBLE_REF1);
        assertEquals(2, roWithTwo.getData().getHumans().size(), "bubble has member");
        Optional<BubbleHumanPartRO> storedMember2 = roWithTwo.getData()
                                                             .getHumans()
                                                             .stream()
                                                             .filter(m -> m.getHumanLocalRef().equals(MEMBER_REF2.getLocalRef())).findAny();
        assertTrue(storedMember2.isPresent(), "member two store");
        assertFalse(storedMember2.get().isStillPart(), "member two is not active");
        deleteMember(BUBBLE_REF1, MEMBER_REF1);
        assertEquals(1, getBubble(BUBBLE_REF1).getData().getHumans().size(), "still one member left");
    }

    private Map<String, String> createURIParameter(BubbleRef bubbleRef)
    {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(BubbleMemberController.PATH_VAR_BUBBLEID, bubbleRef.getLocalRef());
        return urlParams;
    }

    private Map<String, String> createURIParameter(BubbleRef bubbleRef, HumanRef humanRef)
    {
        Map<String, String> urlParams = createURIParameter(bubbleRef);
        urlParams.put(BubbleMemberController.PATH_VAR_HUMANREF, humanRef.getLocalRef());
        return urlParams;
    }

    private BubbleRO getBubble(BubbleRef ref)
    {
        return this.restTemplate.getForObject(BubbleController.PATH_BUBBLE, BubbleRO.class, createURIParameter(ref));
    }

    private void putMember(BubbleRef ref, HumanRef humanRef, BubbleMemberCreateRO data)
    {
        ResponseEntity<Void> response = this.restTemplate.exchange(BubbleMemberController.PATH_MEMBER, HttpMethod.PUT,
                        new HttpEntity<>(data), Void.class, createURIParameter(ref, humanRef));
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }

    private void deleteMember(BubbleRef ref, HumanRef humanRef)
    {
        ResponseEntity<Void> response = this.restTemplate.exchange(BubbleMemberController.PATH_MEMBER, HttpMethod.DELETE,
                        new HttpEntity(new HttpHeaders()), Void.class, createURIParameter(ref, humanRef));
        assertTrue(response.getStatusCode().is2xxSuccessful(), "returned with " + response.getStatusCode());
    }
}
