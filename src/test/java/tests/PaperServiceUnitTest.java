package tests;

import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.service.PaperService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static tests.TestResources.PAPER_MODEL_ID_1_VER_1;

@RunWith(MockitoJUnitRunner.class)
public class PaperServiceUnitTest {
    @Mock
    private PaperRepo paperRepo;

    @InjectMocks
    PaperService service;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canGetViewPaperModelAndView() throws AccessException {
        ModelAndView expected = new ModelAndView("paper");
        expected.addObject("paper", PAPER_MODEL_ID_1_VER_1);
        expected.addObject("answerable", false);
        expected.addObject("dashboardContent", "testLibrary");
        expected.addObject("inMarking", false);

        when(paperRepo.getPaper(eq(1), eq(1))).thenReturn(PAPER_MODEL_ID_1_VER_1);
        ModelAndView actual = service.getViewPaperModelAndView(1, 1);
        Assert.assertThat(expected, new ReflectionEquals(actual, ""));
    }

    @Test
    public void canFailToGetViewPaperModelAndView() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        when(paperRepo.getPaper(eq(1), eq(1))).thenThrow(new AccessException(""));
        service.getViewPaperModelAndView(1, 1);
    }
}
