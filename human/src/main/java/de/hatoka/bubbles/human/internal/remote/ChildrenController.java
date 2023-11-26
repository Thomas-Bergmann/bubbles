package de.hatoka.bubbles.human.internal.remote;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.common.capi.rest.RestControllerErrorSupport;

@RestController
@RequestMapping(value = ChildrenController.PATH_ROOT, produces = { APPLICATION_JSON_VALUE })
public class ChildrenController
{
    public static final String PATH_ROOT = "/humans/{humanId}";
    public static final String PATH_SUB_PARENTS = "/parents";
    public static final String PATH_SUB_CHILDREN = "/children";
    public static final String PATH_SUB_CHILD = "/children/{childId}";
    public static final String PATH_VAR_HUMANID= "humanId";
    public static final String PATH_VAR_CHILDID= "childId";

    @Autowired
    private HumanBORepository humanRepository;

    @Autowired
    private RestControllerErrorSupport errorSupport;

    @GetMapping(PATH_SUB_PARENTS)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getParents(@PathVariable(PATH_VAR_HUMANID) String humanID)
    {
        HumanRef humanRef = HumanRef.localRef(humanID);
        Optional<HumanBO> humanOpt = humanRepository.findHuman(humanRef);
        if (humanOpt.isEmpty())
        {
            errorSupport.throwNotFoundException("notfound.human", humanRef.toString());
        }
        return humanOpt.get().getParents().stream().map(HumanBO::getRef).map(HumanRef::getLocalRef).toList();
    }

    @GetMapping(PATH_SUB_CHILDREN)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getChildren(@PathVariable(PATH_VAR_HUMANID) String humanID)
    {
        HumanRef humanRef = HumanRef.localRef(humanID);
        Optional<HumanBO> humanOpt = humanRepository.findHuman(humanRef);
        if (humanOpt.isEmpty())
        {
            errorSupport.throwNotFoundException("notfound.human", humanRef.toString());
        }
        return humanOpt.get().getChildren().stream().map(HumanBO::getRef).map(HumanRef::getLocalRef).toList();
    }

    @PutMapping(value = PATH_SUB_CHILD, consumes = { APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void addChild(@PathVariable(PATH_VAR_HUMANID) String humanID, @PathVariable(PATH_VAR_CHILDID) String childID)
    {
        HumanRef humanRef = HumanRef.localRef(humanID);
        Optional<HumanBO> humanOpt = humanRepository.findHuman(humanRef);
        if (humanOpt.isEmpty())
        {
            errorSupport.throwNotFoundException("found.human", humanRef.toString());
        }
        Optional<HumanBO> childOpt = humanRepository.findHuman(HumanRef.localRef(childID));
        if (childOpt.isEmpty())
        {
            errorSupport.throwBadRequestException("human.child.notfound", childID);
        }
        humanOpt.get().addChild(childOpt.get());
    }

    @DeleteMapping(PATH_SUB_CHILD)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeChild(@PathVariable(PATH_VAR_HUMANID) String humanID, @PathVariable(PATH_VAR_CHILDID) String childID)
    {
        Optional<HumanBO> humanOpt = humanRepository.findHuman(HumanRef.localRef(humanID));
        Optional<HumanBO> childOpt = humanRepository.findHuman(HumanRef.localRef(childID));
        if (humanOpt.isPresent() && childOpt.isPresent())
        {
            humanOpt.get().removeChild(childOpt.get());
        }
    }
}
