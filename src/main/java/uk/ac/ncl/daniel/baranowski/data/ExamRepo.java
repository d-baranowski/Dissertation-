package uk.ac.ncl.daniel.baranowski.data;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.access.*;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Exam;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.data.exceptions.DateFormatException;
import uk.ac.ncl.daniel.baranowski.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TIME_PATTERN;
import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.ModuleModelMapper.mapModuleModel;
import static uk.ac.ncl.daniel.baranowski.data.mappers.TestDayModelMapper.mapTestDayModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserReferenceModelFrom;

@Repository
public class ExamRepo
{
    private final ExamDAO examDAO;
    private final ModuleDAO moduleDAO;
    private final CandidateDAO candidateDAO;
    private final UserDAO userDao;
    private final TestDayDAO dayDAO;
    private final ModuleRepo moduleRepo;
    private final AttemptRepo attemptRepo;
    private final TermsAndConditionsDAO termsDao;
    private final PaperRepo paperRepo;

    private static final Logger LOGGER = Logger.getLogger(ExamRepo.class.getName());


    //This should always be created using dependency injection
    @Autowired
    public ExamRepo(ExamDAO dao,
                    ModuleDAO moduleDAO,
                    CandidateDAO candidateDAO,
                    UserDAO userDao,
                    TestDayDAO dayDAO,
                    ModuleRepo moduleRepo,
                    AttemptRepo attemptRepo,
                    TermsAndConditionsDAO termsDao,
                    PaperRepo paperRepo) {
        this.termsDao = termsDao;
        this.attemptRepo = attemptRepo;
        this.examDAO = dao;
        this.moduleDAO = moduleDAO;
        this.candidateDAO = candidateDAO;
        this.userDao = userDao;
        this.dayDAO = dayDAO;
        this.moduleRepo = moduleRepo;
        this.paperRepo = paperRepo;
    }


    public int createExam(int moduleId, PaperReferenceModel paper, int termsAndConditionsId, TestDayModel day) throws AccessException {
        TestDayModel dayModel = null;
        try {
            dayModel = mapTestDayModelFrom(dayDAO.getOrCreate(day));
        } catch (DateFormatException e) {
            final String errorMsg = "Exam date is in wrong format for module with id: " + moduleId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }

        Exam crated = new Exam.Builder()
                .setModuleId(moduleId)
                .setPaperId(paper.getId())
                .setPaperVersionNo(paper.getVersionNo())
                .setStatus(ExamStatus.CREATED)
                .setTermsAndConditionsId(termsAndConditionsId)
                .setTestDayId(dayModel.getId())
                .build();

        try {
            int examId = examDAO.create(crated);
            ModuleModel moduleModel = moduleRepo.get(moduleId);
            createAttemptForEachCandidate(paper,moduleModel,dayModel, examId);
            return examId;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to create exam for module: " + moduleId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    private List<AttemptReferenceModel> createAttemptForEachCandidate(PaperReferenceModel paper, ModuleModel moduleModel, TestDayModel dayModel, int examId) throws AccessException {
        List<AttemptReferenceModel> result = new ArrayList<>();

        for (CandidateModel candidateModel:  moduleModel.getStudents()) {
            result.add(attemptRepo.createAndGet(candidateModel, dayModel, paper, ExamStatus.CREATED.name(), examId));
        }

        return result;
    }

    public ModuleModel gerModuleById(int moduleId) throws AccessException {
        try {
            Module module = moduleDAO.read(moduleId);
            List<CandidateModel> candidateModels = getCandidatesByModule(moduleId);

            return mapModuleModel(module,getModuleLeaders(moduleId),candidateModels);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get module with: " + moduleId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    private List<UserReferenceModel> getModuleLeaders(int moduleId) throws AccessException {
        try {
            List<User> moduleLeaders = userDao.getModuleLeaders(moduleId);
            List<UserReferenceModel> result = new ArrayList<>();
            moduleLeaders.forEach(user -> result.add(mapUserReferenceModelFrom(user)));

            return result;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get module leaders for module with id: " + moduleId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    private List<CandidateModel> getCandidatesByModule(int moduleId) throws AccessException {
        try {
            final List<CandidateModel> result = new ArrayList<>();
            candidateDAO.readAllEnrolledToModule(moduleId).forEach(candidate -> result.add(mapCandidateModelFrom(candidate)));
            return result;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get students enrolled to module with id: " + moduleId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public ExamModel getExam(int examId) throws AccessException  {
        try {
            Exam exam = examDAO.read(examId);

            return new ExamModel.Builder()
                    .setAttempts(attemptRepo.getAllAttemptReferencesForExam(examId))
                    .setTermsAndConditions(termsDao.getTermsById(exam.getTermsAndConditionsId()))
                    .setModule(moduleRepo.get(exam.getModuleId()))
                    .setStatus(exam.getStatus())
                    .setPaperRef(paperRepo.getPaperReference(exam.getPaperId(), exam.getPaperVersionNo()))
                    .setId(examId)
                    .setTestDayModel(mapTestDayModelFrom(dayDAO.read(exam.getTestDayId())))
                    .build();
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get exam with id: " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public boolean isModuleLeader(int examId, String userId) throws AccessException {
        try {
            return examDAO.isModuleLeader(examId, userId);
        } catch (DataAccessException e) {
            final String errorMsg = "Unable to determine if user " + userId + " is module leader for exam " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void beginExam(int examId) throws AccessException {
        try {
            examDAO.setExamStatus(examId, ExamStatus.STARTED);
            examDAO.setStartTime(examId, new LocalTime(System.currentTimeMillis()).toString(TIME_PATTERN));
        } catch (DataAccessException e) {
            final String errorMsg = "Unable to start exam " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void endExam(int examId) throws AccessException {
        try {
            examDAO.setExamStatus(examId, ExamStatus.FINISHED);
            String endTime = new LocalTime(System.currentTimeMillis()).toString(TIME_PATTERN);
            examDAO.setEndTime(examId,endTime);
            examDAO.setEndTimeExtra(examId,endTime);
        } catch (DataAccessException e) {
            final String errorMsg = "Unable to start exam " + examId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }
}
