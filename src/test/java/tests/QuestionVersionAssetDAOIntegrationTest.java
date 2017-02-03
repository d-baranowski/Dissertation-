package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionAssetDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class QuestionVersionAssetDAOIntegrationTest {
    @Autowired
    private QuestionVersionAssetDAO dao;

    @Test
    public void getByQuestionVersion() throws Exception {
        dao.getByQuestionVersion(28, 1);
    }

}