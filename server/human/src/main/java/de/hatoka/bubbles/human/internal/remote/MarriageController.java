package de.hatoka.bubbles.human.internal.remote;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hatoka.bubbles.human.capi.business.HumanBO;
import de.hatoka.bubbles.human.capi.business.HumanBORepository;
import de.hatoka.bubbles.human.capi.business.HumanRef;
import de.hatoka.bubbles.human.capi.remote.MarriageDataRO;
import de.hatoka.common.capi.rest.RestControllerErrorSupport;
import de.hatoka.common.capi.value.IncompleteDate;
import jakarta.ws.rs.QueryParam;

@RestController
@RequestMapping(value = MarriageController.PATH_ROOT, produces = { APPLICATION_JSON_VALUE })
public class MarriageController
{
    public static final String PATH_ROOT = "/marriages";
    public static final String PARAM_HUMAN_REF= "humanRef";
    public static final String SUB_PATH_CREATE = "/create";
    public static final String SUB_PATH_REMOVE = "/remove";

    @Autowired
    private HumanBORepository humanRepository;
    @Autowired
    private MarriageROMapper marriageMapper;

    @Autowired
    private RestControllerErrorSupport errorSupport;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarriageDataRO> getMarriages(@QueryParam(PARAM_HUMAN_REF) String humanRef)
    {
        return marriageMapper.apply(getHuman(humanRef).getMariageRelations());
    }

    private HumanBO getHuman(String humanLocalRef)
    {
        HumanRef humanRef = HumanRef.localRef(humanLocalRef);
        Optional<HumanBO> humanOpt = humanRepository.findHuman(humanRef);
        if (humanOpt.isEmpty())
        {
            errorSupport.throwNotFoundException("notfound.human", humanRef.toString());
        }
        return humanOpt.get();
    }

    @PostMapping(value = SUB_PATH_CREATE, consumes = { APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void createMarriage(@RequestBody MarriageDataRO input)
    {
        HumanBO human1 = getHuman(input.getPartner1LocalRef());
        HumanBO human2 = getHuman(input.getPartner2LocalRef());
        human1.addMariageWith(human2, IncompleteDate.valueOf(input.getDateOfStart()), IncompleteDate.valueOf(input.getDateOfEnd()));
    }

    @PostMapping(value = SUB_PATH_REMOVE, consumes = { APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMarriage(@RequestBody MarriageDataRO input)
    {
        HumanBO human1 = getHuman(input.getPartner1LocalRef());
        HumanBO human2 = getHuman(input.getPartner2LocalRef());
        human1.removeMariageWith(human2);
    }
}
