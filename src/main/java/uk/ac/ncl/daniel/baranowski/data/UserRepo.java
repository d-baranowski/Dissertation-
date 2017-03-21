/**
 * Repository to handle user information.
 */
package uk.ac.ncl.daniel.baranowski.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.data.access.UserDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserReferenceModelFrom;

@Repository
public class UserRepo {
    /**
     * Class to handle obtaining user data.
     **/
    private final UserDAO userDao;
    private static final Logger LOGGER = Logger.getLogger(UserRepo.class.getName());

    @Autowired
    public UserRepo(UserDAO userDao) {
        this.userDao = userDao;
    }

    public UserModel getUser(String userId) {
        final User user = userDao.readUser(userId);
        final List<Roles> roles = getRolesForUserModel(user.getId());
        return mapUserModelFrom(user, roles);
    }

    public UserReferenceModel getUserReference(String userId) {
        final User user = userDao.readUser(userId);
        return mapUserReferenceModelFrom(user);
    }

    public UserModel getUserByLogin(String login) {
        User user;
        try {
            user = userDao.readUserByLogin(login);
        } catch(EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE, "There are no users with login" + login, e);
            return null;
        }

        final List<Roles> roles = getRolesForUserModel(user.getId());
        return mapUserModelFrom(user, roles);
    }

    private List<Roles> getRolesForUserModel(String userId) {
        final List<Roles> result = new ArrayList<>();
        userDao.getRolesByUserId(userId).forEach(role -> result.add(Roles.getByName(role.getName())));
        return result;
    }
}
