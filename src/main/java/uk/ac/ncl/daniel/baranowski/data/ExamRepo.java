package uk.ac.ncl.daniel.baranowski.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.access.*;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Exam;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    TermsAndConditionsDAO termsDao) {
        this.termsDao = termsDao;
        this.attemptRepo = attemptRepo;
        this.examDAO = dao;
        this.moduleDAO = moduleDAO;
        this.candidateDAO = candidateDAO;
        this.userDao = userDao;
        this.dayDAO = dayDAO;
        this.moduleRepo = moduleRepo;
    }


    public ExamModel createAndGetExam(int moduleId, PaperReferenceModel paper, int termsAndConditionsId, TestDayModel day) throws AccessException {
        TestDayModel dayModel = mapTestDayModelFrom(dayDAO.getOrCreate(day));
        Exam crated = new Exam.Builder()
                .setModuleId(moduleId)
                .setPaperId(paper.getId())
                .setPaperVersionNo(paper.getVersionNo())
                .setStatus(ExamStatus.CREATED)
                .setTermsAndConditionsId(termsAndConditionsId)
                .setTestDayId(dayModel.getId())
                .build();

        int examId = examDAO.create(crated);

        try {
            String terms = termsDao.getTermsById(termsAndConditionsId);
            ModuleModel moduleModel = moduleRepo.get(moduleId);
            return new ExamModel.Builder()
                    .setTermsAndConditions(terms)
                    .setId(examId)
                    .setModule(moduleModel)
                    .setPaperRef(paper)
                    .setStatus(ExamStatus.CREATED)
                    .setTestDayModel(dayModel)
                    .setAttempts(createAttempForEachCandidate(paper,moduleModel,dayModel,termsAndConditionsId))
                    .build();
        } catch (AccessException e) {
            final String errorMsg = "Failed create exam";
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    private List<AttemptReferenceModel> createAttempForEachCandidate(PaperReferenceModel paper, ModuleModel moduleModel, TestDayModel dayModel, int terms) throws AccessException {
        List<AttemptReferenceModel> result = new ArrayList<>();

        for (CandidateModel candidateModel:  moduleModel.getStudents()) {
            int timeAllowed = candidateModel.getHasExtraTime() ? paper.getTimeAllowed() + (int)(paper.getTimeAllowed() * 0.5) : paper.getTimeAllowed();
            try {
                result.add(attemptRepo.createAndGet(candidateModel, dayModel, paper, ExamStatus.CREATED.name(), terms, timeAllowed));
            } catch (AccessException e) {
                final String errorMsg = "Failed create attempt for candidate: " + candidateModel;
                LOGGER.log(Level.WARNING, errorMsg, e);
                throw new AccessException(errorMsg, e);
            }
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

}
