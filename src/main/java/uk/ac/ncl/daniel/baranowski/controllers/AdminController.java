package uk.ac.ncl.daniel.baranowski.controllers;

import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyAuthority('Admin')")
@RequestMapping("admin")
public class AdminController{

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

        @RequestMapping(value = "/newTerms", method = RequestMethod.POST)
        public String newTerms(@RequestParam(value = "termsField", required = true, defaultValue = "") String terms
                               , RedirectAttributes redirectAttributes){
            adminService.createNewTerms(terms);
            redirectAttributes.addFlashAttribute(Constants.FLASH_SUCCESS, "Successfully updated terms and conditions.");
            return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + "/settings";
        }

}
