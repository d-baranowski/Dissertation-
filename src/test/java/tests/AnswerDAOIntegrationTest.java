package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.AnswerDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
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
public class AnswerDAOIntegrationTest {
    @Autowired
    private AnswerDAO dao;

    @Test()
    public void canRead() {
        Answer expected = new Answer.Builder()
                .setQuesionVersionNo(1)
                .setQuesionId(1)
                .setTestDayEntryId(1)
                .setText("Lorem ipsium dolor ...")
                .build();


        assertEquals(expected, dao.read(1, 1, 1));
    }

    @Test
    public void canSubmitAnswer() {
        Answer expected = new Answer.Builder()
                .setQuesionVersionNo(1)
                .setQuesionId(15)
                .setTestDayEntryId(1)
                .setText("Lorem ipsium dolor ...")
                .build();

        dao.submitAnswer(expected);

        assertEquals(expected, dao.read(1, 15, 1));
    }

    @Test()
    public void canGetCount() {
        assertEquals(1, dao.getCount());
    }
}
