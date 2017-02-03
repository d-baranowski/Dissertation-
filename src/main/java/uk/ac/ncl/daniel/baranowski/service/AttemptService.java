package uk.ac.ncl.daniel.baranowski.service;

import uk.ac.ncl.daniel.baranowski.common.BlobUtils;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.common.enums.AttemptStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.AttemptMissingException;
import uk.ac.ncl.daniel.baranowski.exceptions.InvalidAttemptStatusException;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.views.TestAttemptViewModel;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

@Service
public class AttemptService {
    private final PaperRepo paperRepo;
    private final AttemptRepo attemptRepo;
    private static final Logger LOGGER = Logger.getLogger(AttemptService.class.getName());

    @Value("${defaultTermsAndConditions}")
    private String defaultTermsAndConditions;

    @Autowired
    private AttemptService(PaperRepo paperRepo, AttemptRepo attemptRepo) { //NOSONAR This constructor is private to enforce use of Autowired
        this.paperRepo = paperRepo;
        this.attemptRepo = attemptRepo;
    }

    public void validateStatus(int attemptId, AttemptStatus expectedStatus) {
        try {
            final AttemptStatus currentStatus = AttemptStatus.getByName(attemptRepo.getAttemptStatus(attemptId));
            
            if (!currentStatus.equals(expectedStatus)) {
                final String errorMsg = String.format("Status for test attempt id: %s was %s but was expected to be %s", attemptId,currentStatus.name() ,expectedStatus.name());
                LOGGER.log(Level.INFO, errorMsg);
                throw new InvalidAttemptStatusException(expectedStatus,currentStatus,errorMsg);
            }
        } catch (AccessException e) {
            final String errorMsg = String.format("Attempt with id %s does not exist in the database.", attemptId);
            LOGGER.log(Level.SEVERE,errorMsg , e);
            throw new AttemptMissingException(errorMsg, e);
        }
    }

    public void submitAnswer(SubmitAnswerFormModel formBody, String candidateName) {
        AnswerModel model = new AnswerModel();
        model.setText(formBody.getText());

        if (formBody.getBase64imagePng() != null) {
            final AssetModel answerAsset = new AssetModel();
            final String referenceName = String.format("%s answer for question id: %s version no: %s ", candidateName, formBody.getQuestionId(), formBody.getQuestionVersionNo());
            answerAsset.setReferenceName(referenceName);
            answerAsset.setFileType("png");
            answerAsset.setFile(BlobUtils.png64StringToByteArray(formBody.getBase64imagePng()));

            model.setAssets(answerAsset);
        }

        try {
            attemptRepo.submitAnswer(model,
                    formBody.getAttemptId(),
                    formBody.getQuestionId(),
                    formBody.getQuestionVersionNo()
            );
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to submit answer from form: %s, by Candidate: %s", formBody, candidateName);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            String friendlyError = "Sorry, your answer failed to submit, please check your answer contains no invalid characters" +
                    " and does not exceed 5000 characters and try again. If the problem persists, contact the administrator.";
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, friendlyError);
        }
    }

    public int getTimeAllowed(int testAttemptId) {
        try {
            final AttemptReferenceModel attemptModel = attemptRepo.getAttemptReferenceModel(testAttemptId);
            return attemptModel.getTimeAllowed();
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, String.format("Failed to get time for test attempt id %s", testAttemptId), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ModelAndView getModelAndViewForAttempt(int attemptId) {
        final AttemptModel model;
        final TestAttemptViewModel mav;
        try {
            model = attemptRepo.getAttemptModel(attemptId);
            mav = new TestAttemptViewModel(model);
            mav.hideNavigationBar();
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Failed to get attempt from repository " + attemptId, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return mav.getModelAndView();
    }


    public void beginAttempt(HttpSession candidateSession, int attemptId) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Constants.ROLE_CANDIDATE));
        SessionUtility.setCurrentUserRoles(candidateSession, roles);

        SessionUtility.assignTestAttemptIdToCandidate(candidateSession, attemptId);

        long startTime = System.currentTimeMillis();
        candidateSession.setAttribute(Constants.SESSION_START_TIME, startTime);
        candidateSession.setAttribute(Constants.SESSION_ATTEMPT_ID, attemptId);
        candidateSession.setAttribute(Constants.SESSION_TIME_ALLOWED, getTimeAllowed(attemptId));
        try {

            attemptRepo.setAttemptStatus(AttemptStatus.STARTED, attemptId);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to start attempt with id %s", attemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void finishAttempt(HttpSession candidateSession) {
        try {
            attemptRepo.setAttemptStatus(AttemptStatus.FINISHED, SessionUtility.getCandidatesTestAttemptId(candidateSession));
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Could not finish test with for session " + candidateSession, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ModelAndView getModelAndViewForAttemptSetupPage() {
        final ModelAndView mav = new ModelAndView("admin/setupExam");
        try {
            mav.addObject("paperReferences", paperRepo.getPaperReferencesToLatestVersions());
            mav.addObject("previousCandidates", attemptRepo.getAllCandidates());
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Failed to get paper references", e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        mav.addObject("formBody", new SetupExamFormModel());
        return mav;
    }

    public AttemptReferenceModel createAttemptModelFromSetupInformation(SetupExamFormModel info) {
        CandidateModel candidate = getOrCreateCandidate(info.getCandidate());
        int termsId = attemptRepo.getTermsAndConditionsId();
        try {
            return attemptRepo.createAndGet(candidate, info.getDay(), getPaperReferenceFromForm(info), AttemptStatus.CREATED.name(), termsId, info.getTimeAllowed());
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getTerms(){
        int termsId = attemptRepo.getTermsAndConditionsId();
        return attemptRepo.getTermsById(termsId);
    }

    public AttemptReferenceModel getAttemptReferenceModel(int testAttemptId) {
        try {
            return attemptRepo.getAttemptReferenceModel(testAttemptId);
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private CandidateModel getOrCreateCandidate(CandidateModel candidate) {
        if (!candidate.hasId()) {
            try {
                return attemptRepo.createAndGetCandidate(candidate);
            } catch (AccessException e) {
                final String errorMsg = String.format("Failed to create candidate: %s", candidate);
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
