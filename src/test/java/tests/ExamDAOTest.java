package tests;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.ExamDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Exam;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExamDAOTest {
    @Autowired
    ExamDAO dao;

    @Test
    public void canRead() {
        Assert.assertEquals(TestResources.SAMPLE_EXAM, dao.read(1));
    }

    @Test
    public void canCreate() {
        Exam exam = new Exam.Builder()
                .setId(2)
                .setTestDayId(1)
                .setPaperId(2)
                .setPaperVersionNo(1)
                .setTermsAndConditionsId(1)
                .setStatus("Finished")
                .build();

        int createdId = dao.create(exam);

        Assert.assertEquals(exam, dao.read(createdId));
    }
}
