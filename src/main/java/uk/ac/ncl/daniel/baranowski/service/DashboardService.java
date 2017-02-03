package uk.ac.ncl.daniel.baranowski.service;

import uk.ac.ncl.daniel.baranowski.common.enums.AttemptStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SignedAttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.views.FinishedTestTableViewModel;
import uk.ac.ncl.daniel.baranowski.views.GenerateTestViewModel;
import uk.ac.ncl.daniel.baranowski.views.MarkedTestTableViewModel;
import uk.ac.ncl.daniel.baranowski.views.OngoingMarkingTableViewModel;
import uk.ac.ncl.daniel.baranowski.views.OngoingTestTableViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

@Service
public class DashboardService {
    private final PaperRepo paperRepo;
    private final AttemptRepo attemptRepo;
    private final UserRepo userRepo;
    private static final Logger LOGGER = Logger.getLogger(DashboardService.class.getName());

    @Autowired
    public DashboardService(PaperRepo paperRepo, AttemptRepo attemptRepo, UserRepo userRepo) {
        this.paperRepo = paperRepo;
        this.attemptRepo = attemptRepo;
        this.userRepo = userRepo;
    }

    public ModelAndView getGenerateTestViewModel(List<FieldError> errors, SetupExamFormModel target) {
        try {
            return new GenerateTestViewModel(
                    paperRepo.getPaperReferencesToLatestVersions(),
                    attemptRepo.getAllCandidates())
                    .appendErrors(errors, target)
                    .getModelAndView();
        } catch (AccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get required data", e);
            throw new ResourceAccessException("Failed to get paper references");
        }
    }

    public ModelAndView getViewTestsFragment() {
        ModelAndView mav =  new ModelAndView(Constants.TEMPLATE_DASHBOARD);
        mav.addObject("dashboardContent", "viewTests");
        return mav;
    }

    public ModelAndView getSettingsFragment(){
        ModelAndView mav = new ModelAndView(Constants.TEMPLATE_DASHBOARD);
        int currentTermsId = attemptRepo.getTermsAndConditionsId();
        mav.addObject("currentTerms", attemptRepo.getTermsById(currentTermsId));
        mav.addObject("dashboardContent", "settings");
        return mav;
    }

    public ModelAndView getCurrentTestsFragment() {
        try {
            List<AttemptReferenceModel> referencesByStatus = attemptRepo.getAttemptReferencesByStatus(AttemptStatus.STARTED);
            return new OngoingTestTableViewModel(referencesByStatus).getMav();
        } catch (AccessException e) {
            String errorMsg = "Failed to get attempt references by status ";
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public ModelAndView getMarkedTestsFragment() {
        try {
            List<SignedAttemptReferenceModel> referencesByStatus = getSignedAttemptReferenceModels(attemptRepo.getAttemptReferencesByStatus(AttemptStatus.MARKED));
            return new MarkedTestTableViewModel(referencesByStatus).getMav();
        } catch (AccessException e) {
            String errorMsg = "Failed to get attempt references by status ";
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public ModelAndView getUnmarkedTestsFragment() {
        try {
            List<AttemptReferenceModel> referencesByStatus = attemptRepo.getAttemptReferencesByStatus(AttemptStatus.FINISHED);
            return new FinishedTestTableViewModel(referencesByStatus).getMav();
        } catch (AccessException e) {
            String errorMsg = "Failed to get attempt references by status ";
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public ModelAndView getOngoingMarkingFragment() {
        try {
            List<SignedAttemptReferenceModel> referencesByStatus = getSignedAttemptReferenceModels(attemptRepo.getAttemptReferencesByStatus(AttemptStatus.MARKING_ONGOING));
            return new OngoingMarkingTableViewModel(referencesByStatus).getMav();
        } catch (AccessException e) {
            String errorMsg = "Failed to get attempt references by status ";
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    private List<SignedAttemptReferenceModel> getSignedAttemptReferenceModels(List<AttemptReferenceModel> references) {
        final List<SignedAttemptReferenceModel> result = new ArrayList<>();
        references.forEach(reference -> {
            try {
                final UserReferenceModel userRef = userRepo.getUserReference(attemptRepo.getIdOfUserMarking(reference.getId()));
                result.add(new SignedAttemptReferenceModel(reference,userRef));
            } catch (AccessException e) {
                String errorMsg = "Failed to sign marked attempt references";
                LOGGER.log(Level.SEVERE, errorMsg, e);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
            }
        });

        return result;
    }
}
