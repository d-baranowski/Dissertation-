package tests.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionAssetDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class QuestionVersionAssetDAOIntegrationTest {
    @Autowired
    private QuestionVersionAssetDAO dao;

    @Test
    public void getByQuestionVersion() throws Exception {
        dao.getByQuestionVersion(28, 1);
    }
}