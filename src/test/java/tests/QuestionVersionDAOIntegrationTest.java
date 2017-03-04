package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class QuestionVersionDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private QuestionVersionDAO dao;

    @PostConstruct
    public void init() {
        dao = context.getBean(QuestionVersionDAO.class);
    }

    @Test
    public void canGetQuestionVersionCount() {
        assertEquals(28, dao.getCount());
    }

    @Test
    public void canReadById() {
        Assert.assertEquals(TestResources.QUESTION_VERSION_ID_1_VERNO_1, dao.read(1, 1));
    }

    @Test
    public void canGetVersionsByQuestionId() {
        assertTrue(dao.getVersionsByQuestionId(1).containsAll(new HashSet<>(Arrays.asList(TestResources.QUESTION_VERSION_ID_1_VERNO_1))));
    }

    @Test
    public void canGetVersionsBySectionVersionId() {
        assertTrue(dao.getBySection(1, 1).containsAll(new HashSet<>(Arrays.asList(TestResources.QUESTION_VERSION_ID_1_VERNO_1))));
    }

    @Test
    public void canGetReferenceNumber() {
        assertEquals(new Integer(1), dao.getQuestionReferenceNumber(1, 1, 1, 1));
    }

    @Test
    public void canGetQuestionForSectionSix() {
        List<QuestionVersion> expected = Arrays.asList(TestResources.QUESTION_VERSION_ID_30_VERNO_1, TestResources.QUESTION_VERSION_ID_31_VERNO_1);
        assertEquals(expected, dao.getBySection(6, 1));
    }

}
