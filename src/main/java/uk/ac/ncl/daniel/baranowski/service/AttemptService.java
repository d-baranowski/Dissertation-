package uk.ac.ncl.daniel.baranowski.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.BlobUtils;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.ExamRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.AttemptMissingException;
import uk.ac.ncl.daniel.baranowski.exceptions.InvalidAttemptStatusException;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.views.TestAttemptViewModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_USERNAME;

@Service
public class AttemptService {
    private final PaperRepo paperRepo;
    private final AttemptRepo attemptRepo;
    private final TermsAndConditionsDAO termsDao;
    private final ExamRepo examRepo;
    private static final Logger LOGGER = Logger.getLogger(AttemptService.class.getName());

    @Autowired
    private AttemptService(PaperRepo paperRepo, AttemptRepo attemptRepo, TermsAndConditionsDAO termsDao, ExamRepo examRepo) { //NOSONAR This constructor is private to enforce use of Autowired
        this.paperRepo = paperRepo;
        this.attemptRepo = attemptRepo;
        this.termsDao = termsDao;
        this.examRepo = examRepo;
    }

    public void validateStatus(int attemptId, ExamStatus expectedStatus) {
        try {
            final ExamStatus currentStatus = ExamStatus.getByName(attemptRepo.getAttemptStatus(attemptId));
            
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

    public int getTimeRemaining(int testAttemptId) {
        try {
            return attemptRepo.getTimeRemaining(testAttemptId);
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
        candidateSession.setAttribute(Constants.SESSION_TIME_ALLOWED, getTimeRemaining(attemptId));
        try {
            attemptRepo.setAttemptStatus(ExamStatus.STARTED, attemptId);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to start attempt with id %s", attemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void finishAttempt(HttpSession candidateSession) {
        try {
            attemptRepo.setAttemptStatus(ExamStatus.FINISHED, SessionUtility.getCandidatesTestAttemptId(candidateSession));
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


    public AttemptReferenceModel getAttemptReferenceModel(int testAttemptId) {
        try {
            return attemptRepo.getAttemptReferenceModel(testAttemptId);
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ModelAndView getExamLoginScreen() {
        ModelAndView mav = new ModelAndView(Constants.TEMPLATE_LOGIN);
        mav.addObject("ENDPOINT", ControllerEndpoints.LOGIN_CREATE_SESSION);
        return mav;
    }

    public void setupSessionToBeginAttempt(HttpSession candidateSession, int attemptId) {
        validateStatus(attemptId, ExamStatus.CREATED);
        candidateSession.setAttribute(Constants.SESSION_BEGUN_TEST_ATTEMPT, true);
        candidateSession.setAttribute(SESSION_USERNAME, "Candidate");
        beginAttempt(candidateSession, attemptId);
    }

    public int canLoginToAttempt(int examId, String login, String password) {
        try {
            return attemptRepo.findAttemtIdByCredentials(examId,login,password);
        } catch (AccessException e) {
            String errorMsg = "Wrong credentials log into exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
        }

        return 0;
    }

    @Value("${defaultTermsAndConditions}")
    private String defaultTermsAndConditions;

    public String getLatestTermsAndConditions() {
        Integer termsId = termsDao.getLatestId();

        if (termsId != null) {
            return termsDao.getTermsById(termsId);        }

        return defaultTermsAndConditions;
    }

    public int getExamId(int attemptId) {
        try {
            return attemptRepo.getAttemptReferenceModel(attemptId).getExamId();
        } catch (AccessException e) {
            String errorMsg = "Could not retrieve attempt with id " + attemptId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AttemptMissingException(errorMsg,e);
        }
    }
}
