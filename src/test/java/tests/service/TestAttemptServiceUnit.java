package tests.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.service.AttemptService;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static tests.TestResources.TEST_ATTEMPT_MODEL_ID_1;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_ATTEMPT_ID;
import static uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus.FINISHED;
import static uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus.STARTED;

@RunWith(MockitoJUnitRunner.class)
public class TestAttemptServiceUnit {
    @Mock
    private PaperRepo paperRepo;
    @Mock
    private AttemptRepo attemptRepo;

    @InjectMocks
    AttemptService service;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

   /* @Test
    public void canGetTimeExpected() throws AccessException {
        AttemptReferenceModel mockResult = mock(AttemptReferenceModel.class);
        PaperReferenceModel mockPaper = mock(PaperReferenceModel.class);
        when(mockPaper.getId()).thenReturn(1);
        when(mockPaper.getVersionNo()).thenReturn(1);

        when(mockResult.getPaperRef()).thenReturn(mockPaper);
        when(mockResult.getTimeAllowed()).thenReturn(60);

        doReturn(mockResult).when(attemptRepo).getAttemptReferenceModel(eq(1));
        assertEquals(60, service.getTimeRemaining(1));
    }*/

    @Test
    public void canGetTimeExpectedWithError() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        doThrow(new AccessException("")).when(attemptRepo).getTimeRemaining(eq(1));
        service.getTimeRemaining(1);
    }

    @Test
    public void canGetModelAndViewForAttempt() throws AccessException {
        AttemptModel result = TEST_ATTEMPT_MODEL_ID_1;
        result.setStatus(STARTED.toString());
        when(attemptRepo.getAttemptModel(eq(1))).thenReturn(result);

        ModelAndView expected = new ModelAndView("paper");
        expected.addObject("submitAnswerForm", new SubmitAnswerFormModel());
        expected.addObject("paper", TEST_ATTEMPT_MODEL_ID_1.getPaper());
        expected.addObject("answerable", true);
        expected.addObject("inMarking", false);
        expected.addObject("examMarking", false);
        expected.addObject("noNavigation", true);
        expected.addObject("attemptId", 1);
        expected.addObject("answers", TEST_ATTEMPT_MODEL_ID_1.getAnswerMap());
        expected.addObject("candidateName", TEST_ATTEMPT_MODEL_ID_1.getCandidate().getFullName());
        expected.addObject("totalQuestionsToAnswer", TEST_ATTEMPT_MODEL_ID_1.getPaper().getTotalQuestionsToAnswer());

        ModelAndView actual = service.getModelAndViewForAttempt(1);
        Assert.assertThat(expected, new ReflectionEquals(actual, ""));
    }

    @Test
    public void canGetModelAndViewForAttemptWithError() throws AccessException {
        when(attemptRepo.getAttemptModel(eq(3))).thenThrow(new AccessException(""));
        expectedEx.expect(HttpServerErrorException.class);
        service.getModelAndViewForAttempt(3);
    }

    @Test
    public void canSubmitQuestion() throws AccessException {
        SubmitAnswerFormModel submittedQuestion = new SubmitAnswerFormModel();
        submittedQuestion.setText("Lorem ipsium dolore...");
        submittedQuestion.setQuestionId(1);
        submittedQuestion.setQuestionVersionNo(1);
        submittedQuestion.setAttemptId(1);

        final ArgumentCaptor<AnswerModel> answerModel = ArgumentCaptor.forClass(AnswerModel.class);
        final ArgumentCaptor<Integer> attemptId = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> questionId = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> questionVersionNo = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(attemptRepo).submitAnswer(answerModel.capture(), attemptId.capture(), questionId.capture(), questionVersionNo.capture());

        service.submitAnswer(submittedQuestion, "Daniel Baranowski");

        assertEquals("Lorem ipsium dolore...", answerModel.getValue().getText());
        assertEquals(1, attemptId.getValue().intValue());
        assertEquals(1, questionId.getValue().intValue());
        assertEquals(1, questionVersionNo.getValue().intValue());
    }

    @Test
    public void canSubmitQuestionWithAsset() throws AccessException {
        SubmitAnswerFormModel submittedQuestion = new SubmitAnswerFormModel();
        submittedQuestion.setBase64imagePng("iVBORw0KGgoAAAANSUhEUgAAAOEAAACWCAY");

        final ArgumentCaptor<AnswerModel> answerModel = ArgumentCaptor.forClass(AnswerModel.class);

        doNothing().when(attemptRepo).submitAnswer(answerModel.capture(), anyInt(), anyInt(), anyInt());
        service.submitAnswer(submittedQuestion, "Daniel Baranowski");
        assertEquals("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAACWCAY=", answerModel.getValue().getAsset().getBase64StringFile());
    }

    @Test
    public void canSubmitQuestionWithError() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        SubmitAnswerFormModel submittedQuestion = new SubmitAnswerFormModel();
        submittedQuestion.setText("Lorem ipsium dolore...");
        submittedQuestion.setQuestionId(1);
        submittedQuestion.setQuestionVersionNo(1);
        submittedQuestion.setAttemptId(1);
        doThrow(new AccessException("")).when(attemptRepo).submitAnswer(any(AnswerModel.class), eq(1), eq(1), eq(1));

        service.submitAnswer(submittedQuestion, "Daniel Baranowski");
    }

   /* @Test
    public void canStartTest() throws AccessException {
        HttpSession candidateSession = mock(HttpSession.class);
        AttemptReferenceModel mockResult = mock(AttemptReferenceModel.class);
        PaperReferenceModel mockPaper = mock(PaperReferenceModel.class);
        when(mockPaper.getId()).thenReturn(1);
        when(mockPaper.getVersionNo()).thenReturn(1);

        when(mockResult.getPaperRef()).thenReturn(mockPaper);
        *//*when(mockResult.getTimeAllowed()).thenReturn(60);*//*

        doReturn(mockResult).when(attemptRepo).getAttemptReferenceModel(eq(2));

        ArgumentCaptor<String> attributeName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> attributeValue = ArgumentCaptor.forClass(Object.class);
        doNothing().when(candidateSession).setAttribute(attributeName.capture(), attributeValue.capture());

        ArgumentCaptor<ExamStatus> attemptStatus = ArgumentCaptor.forClass(ExamStatus.class);
        ArgumentCaptor<Integer> attemptId = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(attemptRepo).setAttemptStatus(attemptStatus.capture(), attemptId.capture());

        service.beginAttempt(candidateSession, 2);

        assertTrue(attributeName.getAllValues().containsAll(Arrays.asList(SESSION_START_TIME, SESSION_ATTEMPT_ID, SESSION_TIME_ALLOWED)));
        assertTrue(attributeValue.getAllValues().containsAll(Arrays.asList(2, 60)));
    }*/

    @Test
    public void canFinishTest() throws AccessException {
        HttpSession candidateSession = mock(HttpSession.class);
        when(candidateSession.getAttribute(SESSION_ATTEMPT_ID)).thenReturn(1);

        ArgumentCaptor<ExamStatus> attemptStatus = ArgumentCaptor.forClass(ExamStatus.class);
        ArgumentCaptor<Integer> attemptId = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(attemptRepo).setAttemptStatus(attemptStatus.capture(), attemptId.capture());

        service.finishAttempt(candidateSession);
        assertEquals(1, attemptId.getValue().intValue());
        assertEquals(FINISHED, attemptStatus.getValue());
    }

    @Test
    public void canFailToFinishTest() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        HttpSession candidateSession = mock(HttpSession.class);
        when(candidateSession.getAttribute(SESSION_ATTEMPT_ID)).thenReturn(1);

        doThrow(AccessException.class).when(attemptRepo).setAttemptStatus(anyObject(), anyInt());
        service.finishAttempt(candidateSession);
    }
}
