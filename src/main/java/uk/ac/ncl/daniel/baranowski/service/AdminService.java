package uk.ac.ncl.daniel.baranowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AdminService {
    private PaperRepo paperRepo;
    private AttemptRepo attemptRepo;
    private static final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    // This constructor is private to enforce use of Autowired
    @Autowired
    private AdminService(PaperRepo paperRepo, AttemptRepo attemptRepo) { //NOSONAR
        this.attemptRepo = attemptRepo;
        this.paperRepo = paperRepo;
    }


    public void logOutAdmin(HttpSession adminSession, CandidateModel candidate) {
        SessionUtility.setUserDisplayName(adminSession, candidate.getFirstName(), candidate.getSurname());
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        SessionUtility.setCurrentUserRoles(adminSession, roles);
    }

    public void createNewTerms(String terms){
        paperRepo.setTermsAndConditions(terms);
    }
}
