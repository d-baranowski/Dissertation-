package uk.ac.ncl.daniel.baranowski.service;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.ExamRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.*;
import uk.ac.ncl.daniel.baranowski.models.ExamModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.views.ReviewExamViewModel;

import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.SessionUtility.getUserId;

@Service
public class ExamService {
    private final ExamRepo examRepo;
    private final TermsAndConditionsDAO termsAndConditionsDAO;
    private final ModuleDAO moduleDAO;
    private final PaperRepo paperRepo;

    private static final Logger LOGGER = Logger.getLogger(ExamService.class.getName());


    @Autowired
    private ExamService(ExamRepo examRepo,
                        TermsAndConditionsDAO termsAndConditionsDAO,
                        ModuleDAO moduleDAO,
                        PaperRepo paperRepo) {
        this.examRepo = examRepo;
        this.termsAndConditionsDAO = termsAndConditionsDAO;
        this.moduleDAO = moduleDAO;
        this.paperRepo = paperRepo;
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
}
