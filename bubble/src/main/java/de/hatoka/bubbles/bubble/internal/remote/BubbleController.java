package de.hatoka.bubbles.bubble.internal.remote;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleBORepository;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.capi.remote.BubbleCreateRO;
import de.hatoka.bubbles.bubble.capi.remote.BubbleRO;
import de.hatoka.common.capi.rest.RestControllerErrorSupport;
import de.hatoka.user.capi.business.UserRef;
import jakarta.ws.rs.QueryParam;

@RestController
@RequestMapping(value = BubbleController.PATH_ROOT, produces = { APPLICATION_JSON_VALUE })
public class BubbleController
{
    public static final String PATH_ROOT = "/bubbles";
    public static final String PATH_SUB_BUBBLE = "/{bubbleid}";
    public static final String QUERY_USER_REF = "userRef";
    public static final String PATH_VAR_BUBBLEID= "bubbleid";
    public static final String PATH_BUBBLE = PATH_ROOT + PATH_SUB_BUBBLE;
    
    @Autowired
    private BubbleBORepository bubbleRepository;
    @Autowired
    private BubbleBO2RO bubbleBO2RO;

    @Autowired
    private RestControllerErrorSupport errorSupport;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BubbleRO> getBubbles(@QueryParam(QUERY_USER_REF) String userRef)
    {
        Collection<BubbleBO> bubbles = bubbleRepository.getBubbles(UserRef.localRef(userRef));
        return bubbleBO2RO.apply(bubbles);
    }

    @PutMapping(value = PATH_SUB_BUBBLE, consumes = { APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void createBubble(@PathVariable(PATH_VAR_BUBBLEID) String bubbleID, @RequestBody BubbleCreateRO input)
    {
        if (input.getUserRef() == null || input.getUserRef().isBlank())
        {
            errorSupport.throwBadRequestException("bubble.create.no_user", bubbleID);
        }
        UserRef userRef = UserRef.localRef(input.getUserRef());
        BubbleRef bubbleRef = BubbleRef.localRef(bubbleID);
        Optional<BubbleBO> bubbleOpt = bubbleRepository.findBubble(bubbleRef);
        if (bubbleOpt.isPresent())
        {
            errorSupport.throwNotFoundException("found.bubble", bubbleRef.toString());
        }
        bubbleRepository.createBubble(bubbleRef, input.getName(), userRef);
    }

    @GetMapping(PATH_SUB_BUBBLE)
    @ResponseStatus(HttpStatus.OK)
    public BubbleRO getBubble(@PathVariable(PATH_VAR_BUBBLEID) String bubbleID)
    {
        BubbleRef bubbleRef = BubbleRef.localRef(bubbleID);
        Optional<BubbleBO> bubbleOpt = bubbleRepository.findBubble(bubbleRef);
        if (!bubbleOpt.isPresent())
        {
            errorSupport.throwNotFoundException("notfound.bubble", bubbleRef.toString());
        }
        return bubbleBO2RO.apply(bubbleOpt.get());
    }

    @DeleteMapping(PATH_SUB_BUBBLE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBubble(@PathVariable(PATH_VAR_BUBBLEID) String bubbleID)
    {
        BubbleRef bubbleRef = BubbleRef.localRef(bubbleID);
        Optional<BubbleBO> bubbleOpt = bubbleRepository.findBubble(bubbleRef);
        if (bubbleOpt.isPresent())
        {
            bubbleOpt.get().remove();
        }
    }
}
