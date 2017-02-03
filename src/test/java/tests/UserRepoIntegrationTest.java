package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserModelFrom;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class UserRepoIntegrationTest {

    @Autowired
    UserRepo userRepo;

    @Test
    public void canRetrieveUser() {
        User user = new User.Builder()
                .setId("3ca33b4f-009a-4403-829b-e2d20b3d47c2")
                .setLogin("sampleMarker")
                .setPassword("pass")
                .setName("Bob")
                .setSurname("Smith").build();

        UserModel fetchUser = mapUserModelFrom(user, Arrays.asList(Roles.MARKER));

        UserModel returnedUser = userRepo.getUserByLogin("sampleMarker");

        assertTrue(returnedUser.equals(fetchUser));
    }

    @Test
    public void responseWhenUserDoesntExistTest() {
        UserModel returnedUser = userRepo.getUserByLogin("madeUpUser");
        assertTrue(returnedUser == null);
    }
}
