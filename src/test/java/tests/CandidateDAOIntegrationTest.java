package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.CandidateDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CandidateDAOIntegrationTest {
    @Autowired
    private CandidateDAO dao;

    @Test
    public void aCanGetCount() {
        assertEquals(2, dao.getCount());
    }

    @Test
    public void bCanCreateCandidate() {
        Candidate added = new Candidate.Builder().setName("Daniel").setSurname("Baranowski").setId(dao.getCount() + 1).build();
        dao.create(added);

        assertEquals(added, dao.read(dao.getCount()));
    }

    @Test
    public void canUpdateCandidate() {
        Candidate update = new Candidate.Builder().setId(1).setName("Billy").setSurname("Just").build();
        dao.update(update);
        assertEquals(update, dao.read(1));
    }

    @Test
    public void canRead() {
        Assert.assertEquals(TestResources.CANDIDATE_ID_1, dao.read(1));
    }
}
