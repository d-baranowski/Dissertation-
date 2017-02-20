package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.ExamService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(ControllerEndpoints.EXAM_PREFIX)
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
        } else {
            service.createExamModelFromSetupInformation(formBody, moduleLeaderSession);
        }

        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
    }
}
