package uk.ac.ncl.daniel.baranowski.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.data.access.CandidateDAO;
import uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO;
import uk.ac.ncl.daniel.baranowski.data.access.UserDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.ModuleModelMapper.mapModuleModel;
import static uk.ac.ncl.daniel.baranowski.data.mappers.ModuleModelMapper.mapModuleReferenceModel;
import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserReferenceModelFrom;

@Repository
public class ModuleRepo {
    private final ModuleDAO dao;
    private final CandidateDAO candidateDAO;
    private final UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger(ModuleRepo.class.getName());

    //This should always be created using dependency injection
    @Autowired
    public ModuleRepo(ModuleDAO dao, CandidateDAO candidateDAO, UserDAO userDAO) {
        this.dao = dao;
        this.candidateDAO = candidateDAO;
        this.userDAO = userDAO;
    }

    public List<ModuleReferenceModel> getAllReferencesForLeader(String leaderId) throws AccessException {
        try {
            List<ModuleReferenceModel> result = new ArrayList<>();
            dao.readAllForModuleLeader(leaderId).forEach(
                    module -> result.add(mapModuleReferenceModel(module))
            );
            return result;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get modules for module leader with id " + leaderId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }


    public ModuleModel get(int id) throws AccessException {
        try {
            Module module =  dao.read(id);

            return mapModuleModel(
                    module,
                    getModuleLeaders(id),
                    getAllCandidatesEnrolledToModule(id)
            );
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get module with id " + id;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public List<CandidateModel> getAllCandidatesEnrolledToModule(int moduleId) {
        List<CandidateModel> result = new ArrayList<>();
        candidateDAO.readAllEnrolledToModule(moduleId).forEach(
                candidate -> result.add(mapCandidateModelFrom(candidate))
        );

        return result;
    }
    
    public List<UserReferenceModel> getModuleLeaders(int moduleId) {
        List<UserReferenceModel> result = new ArrayList<>();
        userDAO.getModuleLeaders(moduleId).forEach(user -> result.add(mapUserReferenceModelFrom(user)));
        return result;
    }
}
