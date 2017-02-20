package uk.ac.ncl.daniel.baranowski.service;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import uk.ac.ncl.daniel.baranowski.data.ExamRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.InvalidUserStateException;
import uk.ac.ncl.daniel.baranowski.models.ExamModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;

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

    public ExamModel createExamModelFromSetupInformation(SetupExamFormModel formBody, HttpSession moduleLeaderSession) {
        String userId = getUserId(moduleLeaderSession);
        if (moduleDAO.isModuleLeader(formBody.getModuleId(), userId)) {
            try {
                ModuleModel module = examRepo.gerModuleById(formBody.getModuleId());

                return examRepo.createAndGetExam(
                        module.getId(),
                        getPaperReferenceFromForm(formBody),
                        termsAndConditionsDAO.getLatestId(),
                        appendEndTime(formBody.getDay(), formBody.getTimeAllowed()));
            } catch (AccessException e) {
                String errorMsg = "Failed to get attempt references by status ";
                LOGGER.log(Level.SEVERE, errorMsg, e);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
            }

        } else {
            throw new InvalidUserStateException("Logged in user is not the module leader for chosen module");
        }
    }

    private TestDayModel appendEndTime(TestDayModel day, int timeAllowed) {
        LocalTime startTime = day.getStartTime();
        LocalTime endTime = startTime.plusMinutes(timeAllowed + (int)(0.5*timeAllowed));
        day.setEndTime(endTime);
        return day;
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
