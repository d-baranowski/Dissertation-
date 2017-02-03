package uk.ac.ncl.daniel.baranowski.service;

import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.common.enums.AttemptStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

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

    public AttemptReferenceModel createTestAttemptModelFromSetupInformation(SetupExamFormModel info, String terms) {
        CandidateModel candidate = getOrCreateCandidate(info.getCandidate());

        try {
            return attemptRepo.createAndGet(candidate, info.getDay(), getPaperReferenceFromForm(info), AttemptStatus.CREATED.name(), attemptRepo.getTermsAndConditionsId(), info.getTimeAllowed());
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void logOutAdmin(HttpSession adminSession, CandidateModel candidate) {
        SessionUtility.setUserDisplayName(adminSession, candidate.getFirstName(), candidate.getSurname());
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        SessionUtility.setCurrentUserRoles(adminSession, roles);

    }

    public void createNewTerms(String terms){
        paperRepo.setTermsAndConditions(terms);
    }

    private CandidateModel getOrCreateCandidate(CandidateModel candidate) {
        if (!candidate.hasId()) {
            try {
                return attemptRepo.createAndGetCandidate(candidate);
            } catch (AccessException e) {
                final String errorMsg = String.format("Failed to submit candidate: %s", candidate);
                LOGGER.log(Level.WARNING, errorMsg, e);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                return attemptRepo.getCandidate(candidate.getId());
            } catch (AccessException e) {
                String errorMsg = String.format("Could not get candidate: %s ", candidate);
                LOGGER.log(Level.SEVERE, errorMsg, e);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private PaperReferenceModel getPaperReferenceFromForm(SetupExamFormModel info) throws AccessException {
        try {
            return paperRepo.getPaperReference(info.getPaperId(), paperRepo.getLatestVersionNo(info.getPaperId()));
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to get paper reference from form: %s", info);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }
}
