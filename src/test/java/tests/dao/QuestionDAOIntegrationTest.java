package tests.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tests.TestResources;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class QuestionDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private QuestionDAO dao;

    @PostConstruct
    public void init() {
        dao = context.getBean(QuestionDAO.class);
    }

    @Test
    public void canGetCount() {
        assertEquals(41, dao.getCount());
    }

    @Test
    public void canGetTypeCount() {
        assertEquals(5, dao.getTypeCount());
    }

    @Test
    public void canReadQuestion() {
        Question expected = TestResources.QUESTION_ID_1;
        assertEquals(expected, dao.read(1));
    }
}
