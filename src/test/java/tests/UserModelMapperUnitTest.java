package tests;

import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import java.util.Arrays;
import org.junit.Test;

import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserModelFrom;
import static junit.framework.TestCase.assertTrue;
import static tests.TestResources.USER_ALL;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;

public class UserModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToUserModel() throws IllegalAccessException {
        final UserModel before = new UserModel();
        final UserModel after = mapUserModelFrom(USER_ALL, Arrays.asList(Roles.ADMIN, Roles.AUTHOR, Roles.MARKER));
        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
