package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.ExamService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

@Controller
@RequestMapping(EXAM_PREFIX)
public class ExamController {
    private final ExamService service;

    @Autowired
    public ExamController(ExamService service) {
        this.service = service;
    }


    @RequestMapping(value = ControllerEndpoints.EXAM_SETUP_COMPLETE, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ModuleLeader')")
    public String confirmSetup(@Valid SetupExamFormModel formBody, BindingResult bindingResult, RedirectAttributes redir,HttpSession moduleLeaderSession) {
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("errors", bindingResult.getFieldErrors());
            redir.addFlashAttribute("target", bindingResult.getTarget());
            return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
        } else {
            int examId = service.createExamModelFromSetupInformation(formBody, moduleLeaderSession);
            return ControllerEndpoints.REDIRECT_PREFIX + EXAM_PREFIX + EXAM_REVIEW.replaceFirst("\\{examId}", examId + "");
        }
    }

    @RequestMapping(EXAM_REVIEW)
    @PreAuthorize("hasAnyAuthority('ModuleLeader')")
    public ModelAndView reviewExam(HttpSession moduleLeaderSession, @PathVariable int examId) {
        return service.generateReviewExamView(examId,moduleLeaderSession).getModelAndView();
    }

    @RequestMapping(EXAM_BEGIN)
    @PreAuthorize("hasAnyAuthority('ModuleLeader')")
    public String beginExam(@PathVariable int examId, HttpSession moduleLeaderSession) {
        service.validateStatus(examId, ExamStatus.CREATED);
        service.validateUser(examId, moduleLeaderSession);
        service.beginExam(examId);
        return ControllerEndpoints.REDIRECT_PREFIX + EXAM_PREFIX + EXAM_REVIEW.replaceFirst("\\{examId}", examId + "");
    }

    @RequestMapping(EXAM_FINNISH)
    @PreAuthorize("hasAnyAuthority('ModuleLeader')")
    public String endExam(@PathVariable int examId, HttpSession moduleLeaderSession) {
        service.validateStatus(examId, ExamStatus.STARTED);
        service.validateUser(examId, moduleLeaderSession);
        service.finnishExam(examId);
        return ControllerEndpoints.REDIRECT_PREFIX + EXAM_PREFIX + EXAM_REVIEW.replaceFirst("\\{examId}", examId + "");
    }
}
