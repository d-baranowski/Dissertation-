package uk.ac.ncl.daniel.baranowski.service;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.ExamRepo;
import uk.ac.ncl.daniel.baranowski.data.MarksRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.*;
import uk.ac.ncl.daniel.baranowski.marking.AutoMarkerHelper;
import uk.ac.ncl.daniel.baranowski.models.*;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitMarkFormModel;
import uk.ac.ncl.daniel.baranowski.views.ReviewExamViewModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static uk.ac.ncl.daniel.baranowski.common.SessionUtility.getUserId;

@Service
public class ExamService {
    private final ExamRepo examRepo;
    private final TermsAndConditionsDAO termsAndConditionsDAO;
    private final ModuleDAO moduleDAO;
    private final PaperRepo paperRepo;
    private final AttemptRepo attemptRepo;
    private final MarkingService markingService;
    private final MarksRepo marksRepo;
    private final AutoMarkerHelper autoMarkerHelper;

    private static final Logger LOGGER = Logger.getLogger(ExamService.class.getName());



    @Autowired
    private ExamService(ExamRepo examRepo,
                        TermsAndConditionsDAO termsAndConditionsDAO,
                        ModuleDAO moduleDAO,
                        PaperRepo paperRepo,
                        MarkingService markingService,
                        MarksRepo marksRepo,
                        AttemptRepo attemptRepo,
                        AutoMarkerHelper autoMarkerHelper) {
        this.examRepo = examRepo;
        this.termsAndConditionsDAO = termsAndConditionsDAO;
        this.moduleDAO = moduleDAO;
        this.paperRepo = paperRepo;
        this.markingService = markingService;
        this.marksRepo = marksRepo;
        this.attemptRepo = attemptRepo;
        this.autoMarkerHelper = autoMarkerHelper;
    }

    public int createExamModelFromSetupInformation(SetupExamFormModel formBody, HttpSession moduleLeaderSession) {
        String userId = getUserId(moduleLeaderSession);
        if (moduleDAO.isModuleLeader(formBody.getModuleId(), userId)) {
            try {
                ModuleModel module = examRepo.gerModuleById(formBody.getModuleId());

                return examRepo.createExam(
                        module.getId(),
                        getPaperReferenceFromForm(formBody),
                        termsAndConditionsDAO.getLatestId(),
                        appendEndTime(formBody.getDay(), formBody.getTimeAllowed()));
            } catch (AccessException e) {
                String errorMsg = "Failed to create exam model from setup information";
                LOGGER.log(Level.SEVERE, errorMsg, e);
                throw new FailedToCreateExamException(errorMsg, e);
            }

        } else {
            throw new InvalidUserStateException("Logged in user is not the module leader for chosen module");
        }
    }

    public ReviewExamViewModel generateReviewExamView(int examId, HttpSession moduleLeaderSession) {
        String userId = getUserId(moduleLeaderSession);
        ExamModel examModel;
        try {
            examModel = examRepo.getExam(examId);
        } catch (AccessException e) {
            final String errorMsg = "Failed to get exam with id: " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new ExamMissingException(errorMsg, e);
        }
        if (moduleDAO.isModuleLeader(examModel.getModule().getId(),userId)) {
            return new ReviewExamViewModel(examModel);
        } else {
            throw new InvalidUserStateException("Logged in user is not the module leader for chosen module");
        }
    }

    public boolean isMarking(int examId, HttpSession session) {
        String userId = getUserId(session);
        try {
            return examRepo.isMarking(examId, userId);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to determine if user %s is marking exam %s ", userId, examId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void validateIsMarking(int examId, HttpSession session) {
        if (!isMarking(examId,session)) {
            final String errorMsg = "Logged in user is not markign exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void startMarking(int examId, HttpSession moduleLeaderSession) {
        try {
            String userId = getUserId(moduleLeaderSession);
            ExamModel exam   = examRepo.getExam(examId);
            examRepo.startMarking(examId,userId);
            for (AttemptReferenceModel attempt : exam.getAttempts()) {
                markingService.startMarking(attempt.getId(), moduleLeaderSession);
            }

        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to start marking exam with id %s", examId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void validateUser(int examId, HttpSession moduleLeaderSession) {
        String userId = getUserId(moduleLeaderSession);
        try {
            boolean isModuleLeader = examRepo.isModuleLeader(examId, userId);

            if (!isModuleLeader) {
                throw new InvalidUserStateException("Logged in user is not the module leader for chosen module");
            }

        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to determine if user %s is module leader for exam %s", userId, examId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void beginExam(int examId) {
        try {
            examRepo.beginExam(examId);
        } catch (AccessException e) {
            final String errorMsg = "Failed to start exam " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void finnishExam(int examId) {
        try {
            examRepo.endExam(examId);
            autoMarkExam(examId);
        } catch (AccessException e) {
            final String errorMsg = "Failed to start exam " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void validateStatus(int examId, ExamStatus expectedStatus) {
        try {
            final ExamStatus currentStatus = examRepo.getExam(examId).getStatus();

            if (!currentStatus.equals(expectedStatus)) {
                final String errorMsg = String.format("Status for exam with id: %s was %s but was expected to be %s", examId,currentStatus.name() ,expectedStatus.name());
                LOGGER.log(Level.INFO, errorMsg);
                throw new InvalidAttemptStatusException(expectedStatus,currentStatus,errorMsg);
            }
        } catch (AccessException e) {
            final String errorMsg = String.format("Exam with id %s does not exist in the database.", examId);
            LOGGER.log(Level.SEVERE,errorMsg , e);
            throw new AttemptMissingException(errorMsg, e);
        }
    }

    private TestDayModel appendEndTime(TestDayModel day, int timeAllowed) {
        LocalTime startTime = day.getStartTime();
        LocalTime endTime = startTime.plusMinutes(timeAllowed);
        LocalTime endTimeExtra = endTime.plusMinutes((int)(timeAllowed * 0.5));
        day.setEndTime(endTime);
        day.setEndTimeWithExtraTime(endTimeExtra);
        return day;
    }

    private PaperReferenceModel getPaperReferenceFromForm(SetupExamFormModel info) {
        try {
            return paperRepo.getPaperReference(info.getPaperId(), paperRepo.getLatestVersionNo(info.getPaperId()));
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to get paper reference from form: %s", info);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new TestPaperDoesNotExistException(errorMsg);
        }
    }

    public ModelAndView getMarkingModelAndView(int examId) {
        final ModelAndView mav = new ModelAndView("paper");
        try {
            ExamModel exam = examRepo.getExam(examId);
            PaperModel paper = paperRepo.getPaper(exam.getPaperRef().getId(), exam.getPaperRef().getVersionNo());
            List<AttemptModel> attempts = new ArrayList<>();

            for (AttemptReferenceModel aref : exam.getAttempts()) {
                attempts.add(attemptRepo.getAttemptModel(aref.getId()));
            }

            mav.addObject("paper", paper);
            mav.addObject("answerable", false);
            mav.addObject("inMarking", true);
            mav.addObject("examMarking", true);
            mav.addObject("examId", examId);
            mav.addObject("totalQuestionsToAnswer", paper.getTotalQuestionsToAnswer());
            mav.addObject("submitMarkForm", new SubmitMarkFormModel());
            mav.addObject("takenOnDate", exam.getTestDayModel().getDate());
            mav.addObject("examAttempts", attempts);
            return mav;
        } catch (AccessException e) {
            final String errorMsg = "Failed to get marking view model for exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void prepareExamForMarking(int examId, HttpSession moduleLeaderSession) {
        if (!isMarking(examId, moduleLeaderSession)) {
            validateStatus(examId, ExamStatus.FINISHED);
            startMarking(examId,moduleLeaderSession);
        } else {
            validateStatus(examId, ExamStatus.MARKING_ONGOING);
        }
    }

    public void unlockExamFromMarker(int examId, HttpSession moduleLeaderSession) {
        validateStatus(examId, ExamStatus.MARKING_ONGOING);
        validateIsMarking(examId, moduleLeaderSession);
        String userId = getUserId(moduleLeaderSession);

        try {
            ExamModel exam = examRepo.getExam(examId);
            examRepo.unlockFromMarker(examId);
            for (AttemptReferenceModel attempt : exam.getAttempts()) {
                markingService.validateUserIsMarking(attempt.getId(), userId);
                markingService.unlockMarking(attempt.getId());
            }
        } catch (AccessException e) {
            final String errorMsg = "Failed to unlock exam from marking for exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void autoMarkExam(int examId) {
        AnswerModel freshAnswerModel = new AnswerModel();
        try {
            ExamModel exam = examRepo.getExam(examId);
            PaperModel paper = paperRepo.getPaper(exam.getPaperRef().getId(), exam.getPaperRef().getVersionNo());

            for (AttemptReferenceModel attempt : exam.getAttempts()) {
                AnswersMapModel answers = attemptRepo.getAnswersForAttempt(attempt.getId(), paper);
                for (int sectionKey : paper.getSections().keySet()) {
                    SectionModel section = paper.getSections().get(sectionKey);
                    for (int questionKey : section.getQuestions().keySet()) {
                        QuestionModel question = section.getQuestions().get(questionKey);
                        AnswerModel candidateAnswer = answers.get(sectionKey,questionKey);
                        if (candidateAnswer != freshAnswerModel) {
                            MarkModel mark = autoMarkerHelper.getMark(question,candidateAnswer);
                            if (mark != null) {
                                int markId = marksRepo.submitMark(mark);
                                attemptRepo.markAnswer(attempt.getId(), question.getId(), question.getVersionNo(), markId);
                            }
                        }
                    }
                }
            }
        } catch (AccessException e) {
            final String errorMsg = "Failed to auto mark exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }

    public void finnishMarkingExam(int examId, HttpSession moduleLeaderSession) {
        validateStatus(examId, ExamStatus.MARKING_ONGOING);
        validateIsMarking(examId, moduleLeaderSession);
        String userId = getUserId(moduleLeaderSession);

        try {
            ExamModel exam = examRepo.getExam(examId);
            examRepo.finnishMarkingExam(examId);
            for (AttemptReferenceModel attempt : exam.getAttempts()) {
                markingService.validateUserIsMarking(attempt.getId(), userId);
                markingService.finishMarkingTestAttempt(attempt.getId());
            }
        } catch (AccessException e) {
            final String errorMsg = "Failed to finnish marking exam with id " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new HttpServerErrorException(INTERNAL_SERVER_ERROR, errorMsg);
        }
    }
}
