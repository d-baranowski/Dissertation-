package uk.ac.ncl.daniel.baranowski.data;

import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.data.access.MarkDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import static uk.ac.ncl.daniel.baranowski.data.mappers.MarkModelMapper.mapMarkFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.MarkModelMapper.mapMarkModelFrom;

@Repository
public class MarksRepo {
    private final MarkDAO markDao;
    private final UserRepo userRepo;
    private static final Logger LOGGER = Logger.getLogger(MarksRepo.class.getName());

    //This should always be created using dependency injection
    @Autowired
    public MarksRepo(MarkDAO markDao, UserRepo userRepo) { //NOSONAR
        this.markDao = markDao;
        this.userRepo = userRepo;
    }

    public int submitMark(MarkModel mark) throws AccessException {
        try {
           return markDao.submitMark(mapMarkFrom(mark));
        } catch (DataAccessException e) {
            final String errorMsg = "Failed submit mark: " + mark;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public MarkModel get(int objectId) throws AccessException {
        try {
            final Mark mark = markDao.read(objectId);
            final UserReferenceModel user = userRepo.getUserReference(mark.getMarkerId());
            return mapMarkModelFrom(mark, user);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed get mark by id: " + objectId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public int getSumOfMarksForAttempt(int objectId) throws AccessException {
        try {
            return markDao.getMarkSumForTestDayEntry(objectId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE, "No marks for test attempt with id:  " + objectId, e);
            return 0;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get sum of marks for test attempt with id: " + objectId;
            LOGGER.log(Level.WARNING, errorMsg);
            throw new AccessException(errorMsg, e);
        }
    }
}
