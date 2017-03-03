package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.exceptions.FailedToAddQuestionToSectionException;
import uk.ac.ncl.daniel.baranowski.exceptions.FailedToMoveQuestionWithinSectionException;
import uk.ac.ncl.daniel.baranowski.exceptions.FailedToRemoveQuestionFromSectionException;
import uk.ac.ncl.daniel.baranowski.models.api.AddQuestionToSection;
import uk.ac.ncl.daniel.baranowski.models.api.MoveQuestionInSection;
import uk.ac.ncl.daniel.baranowski.models.api.RemoveQuestionFromSection;
import uk.ac.ncl.daniel.baranowski.service.PaperService;

import javax.validation.Valid;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

@RestController
@RequestMapping(ControllerEndpoints.PAPER_PREFIX)
public class PaperAPIController {
    private final PaperService service;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param service NOSONAR
     */
    @Autowired
    public PaperAPIController(PaperService service) {
        this.service = service;
    }

    @RequestMapping(value = PAPER_ADD_QUESTION_TO_SECTION, method = RequestMethod.POST)
    public ResponseEntity addQuestionToSection(@Valid @RequestBody AddQuestionToSection q, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            int questionNumber = service.addQuestionToSection(q);

            if (questionNumber == -1) {
                return ResponseEntity.badRequest().body(new FailedToAddQuestionToSectionException("Question already added"));
            }

            return ResponseEntity.ok(questionNumber);
        } catch (FailedToAddQuestionToSectionException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = PAPER_REMOVE_QUESTION_FROM_SECTION, method = RequestMethod.POST)
    public ResponseEntity removeQuestionFromSection(@Valid @RequestBody RemoveQuestionFromSection q, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            service.removeQuestionFromSection(q);
            return ResponseEntity.ok().build();
        } catch (FailedToRemoveQuestionFromSectionException | FailedToMoveQuestionWithinSectionException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = PAPER_MOVE_QUESTION_IN_SECTION, method = RequestMethod.POST)
    public ResponseEntity moveQuestionInSection(@Valid @RequestBody MoveQuestionInSection[] q, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            service.moveQuestionWithinSection(q);
            return ResponseEntity.ok("Ok");
        } catch (FailedToMoveQuestionWithinSectionException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = PAPER_GET_UPDATED_SECTION_QUESTIONS, method = RequestMethod.GET)
    public ModelAndView getUpdatedSectionQuestions(@PathVariable int sectionId, @PathVariable int sectionVersion) {
        return service.getSectionQuestionsTableBody(sectionId,sectionVersion);
    }
}
