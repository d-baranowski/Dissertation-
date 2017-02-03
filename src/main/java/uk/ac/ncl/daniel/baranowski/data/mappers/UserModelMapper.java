package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.UserModel;

import java.util.List;

public class UserModelMapper {
    private UserModelMapper() {
        //Hiding implicit public constructor
    }

    public static UserModel mapUserModelFrom(User user, List<Roles> roles) {
        final UserModel result = new UserModel();
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        result.setLogin(user.getLogin());
        result.setPassword(user.getPassword());
        result.setId(user.getId());
        result.setRoles(roles);
        return result;
    }

    public static UserReferenceModel mapUserReferenceModelFrom(User user) {
        final UserReferenceModel result = new UserReferenceModel();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        return result;
    }
}
