package de.hatoka.bubbles.bubble.internal.remote;

import de.hatoka.bubbles.bubble.capi.business.BubbleBO;
import de.hatoka.bubbles.bubble.capi.business.BubbleBORepository;
import de.hatoka.bubbles.bubble.capi.business.BubbleRef;
import de.hatoka.bubbles.bubble.capi.remote.BubbleMemberCreateRO;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.common.capi.rest.RestControllerErrorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = BubbleMemberController.PATH_ROOT, produces = { APPLICATION_JSON_VALUE })
public class BubbleMemberController
{
    static final String PATH_ROOT = "/bubbles/{bubbleid}/members";
    private static final String PATH_SUB_MEMBER = "/{humanRef}";
    static final String PATH_MEMBER = PATH_ROOT + PATH_SUB_MEMBER;
    static final String PATH_VAR_BUBBLEID = "bubbleid";
    static final String PATH_VAR_HUMANREF = "humanRef";

    @Autowired
    private BubbleBORepository bubbleRepository;
    @Autowired
    private BubbleBO2RO bubbleBO2RO;

    @Autowired
    private RestControllerErrorSupport errorSupport;

    @PutMapping(value = PATH_SUB_MEMBER, consumes = { APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void createMember(@PathVariable(PATH_VAR_BUBBLEID) String bubbleID,
                    @PathVariable(PATH_VAR_HUMANREF) String humanId, @RequestBody BubbleMemberCreateRO input)
    {
        BubbleRef bubbleRef = BubbleRef.localRef(bubbleID);
        HumanRef humanRef = HumanRef.localRef(humanId);
        Optional<BubbleBO> bubbleOpt = bubbleRepository.findBubble(bubbleRef);
        if (!bubbleOpt.isPresent())
        {
            errorSupport.throwNotFoundException("notfound.bubble", bubbleRef.toString());
        }
        bubbleOpt.get().add(humanRef).setActive(input.isStillPart());
    }

    @DeleteMapping(value = PATH_SUB_MEMBER)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(@PathVariable(PATH_VAR_BUBBLEID) String bubbleID,
                    @PathVariable(PATH_VAR_HUMANREF) String humanId)
    {
        BubbleRef bubbleRef = BubbleRef.localRef(bubbleID);
        HumanRef humanRef = HumanRef.localRef(humanId);
        Optional<BubbleBO> bubbleOpt = bubbleRepository.findBubble(bubbleRef);
        if (!bubbleOpt.isPresent())
        {
            errorSupport.throwNotFoundException("notfound.bubble", bubbleRef.toString());
        }
        bubbleOpt.get().remove(humanRef);
    }
}
