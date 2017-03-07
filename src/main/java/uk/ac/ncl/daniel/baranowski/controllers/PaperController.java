package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;
import static uk.ac.ncl.daniel.baranowski.common.SessionUtility.getUserId;

/**
 * This controller is responsible for the View Paper functionality of our application.
 */
@Controller
@RequestMapping(ControllerEndpoints.PAPER_PREFIX)
@PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author')")
public class PaperController {

    private final PaperService service;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param service NOSONAR
     */
    @Autowired
    public PaperController(PaperService service) {
        this.service = service;
    }

    /**
     * Uses the variables from the path to display the view paper page to the user.
     * Different test papers will be resolved based on the path variables
     * @param id Id of test paper.
     * @param versionNo  Version number of test paper.
     * @return A page with a test paper. No marking or Answering functionality is provided here.
     */
    @RequestMapping(ControllerEndpoints.PAPER_VIEW)
    public ModelAndView viewPaper(@PathVariable("paperId") int id, @PathVariable("paperVersionNo") int versionNo) {
        return service.getViewPaperModelAndView(id, versionNo);
    }

    /**
     * User can find a paper he wants to view through this page.
     * @return A page containing a list of all the test papers and their versions.
     */
    @RequestMapping("test-library")
    public ModelAndView testLibrary() {
        return service.getViewTestLibrary();
    }

    @RequestMapping(PAPER_QUESTION_EDITOR)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ModelAndView questionEditor(@RequestParam(required = false, defaultValue = "0") int questionId,
                                       @RequestParam(required = false, defaultValue = "0") int questionVersion) {

        return service.getQuestionEditor(questionId,questionVersion);
    }

    @RequestMapping(PAPER_SECTION_EDITOR)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ModelAndView sectionEditor(
            @RequestParam(required = false, defaultValue = "0") int sectionId,
            @RequestParam(required = false, defaultValue = "0") int sectionVersion) {
        return service.getSectionEditor(sectionId, sectionVersion);
    }

    @RequestMapping("paper-editor")
    @PreAuthorize("hasAnyAuthority('Author')")
    public ModelAndView paperEditor(
            @RequestParam(required = false, defaultValue = "0") int paperId,
            @RequestParam(required = false, defaultValue = "0") int paperVersion) {
        return service.getPaperEditor(paperId, paperVersion);
    }

    @RequestMapping(PAPER_VIEW_QUESTION)
    @PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author','ModuleLeader')")
    public ModelAndView viewQuestion(@PathVariable int questionId, @PathVariable int questionVersion) {
        return service.getViewQuestion(questionId, questionVersion);
    }

    @RequestMapping(PAPER_VIEW_SECTION)
    @PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author','ModuleLeader')")
    public ModelAndView viewSection(@PathVariable int sectionId, @PathVariable int sectionVersion) {
        return service.getViewSection(sectionId, sectionVersion);
    }


    /* API ENDPOINTS */
    @RequestMapping(value = PAPER_CREATE_QUESTION, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity createQuestion(@Valid QuestionModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int id = service.createQuestion(model);
        if (id != -1) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value=PAPER_UPDATE_QUESTION, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity updateQuestion(@Valid QuestionModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int versionNo = service.updateQuestion(model);
        if (versionNo != -1) {
            return ResponseEntity.ok(versionNo);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = PAPER_CREATE_SECTION, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity createSection(@Valid SectionModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int id = service.createSection(model);
        if (id != -1) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value=PAPER_UPDATE_SECTION, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity updateSection(@Valid SectionModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int versionNo = service.updateSection(model);
        if (versionNo != -1) {
            return ResponseEntity.ok(versionNo);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = PAPER_CREATE_PAPER, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity createPaper(@Valid PaperModel model, BindingResult bindingResult, HttpSession authorSession) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int id = service.createPaper(model, getUserId(authorSession));
        if (id != -1) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value=PAPER_UPDATE_PAPER, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Author')")
    public ResponseEntity updatePaper(@Valid PaperModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        int versionNo = service.updatePaper(model);
        if (versionNo != -1) {
            return ResponseEntity.ok(versionNo);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
