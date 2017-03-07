package uk.ac.ncl.daniel.baranowski.simulation;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.ExamRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.*;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.service.AttemptService;
import uk.ac.ncl.daniel.baranowski.service.ExamService;
import uk.ac.ncl.daniel.baranowski.simulation.annotations.Simulator;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.Constants.RANDOM_CODE;
import static uk.ac.ncl.daniel.baranowski.common.Constants.RANDOM_IMAGE_ENCODED;

@Simulator
public class ExamSimulator {
    private final ExamRepo examRepo;
    private final ExamService examService;
    private final AttemptService attemptService;
    private final AttemptRepo attemptRepo;
    private final PaperRepo paperRepo;
    private final Random rn;
    private final LoremIpsum loremIpsum;

    private static final Logger LOGGER = Logger.getLogger(ExamSimulator.class.getName());

    @Autowired
    public ExamSimulator(ExamRepo examRepo, ExamService examService, AttemptService attemptService, AttemptRepo attemptRepo, PaperRepo paperRepo) {
        this.examRepo = examRepo;
        this.examService = examService;
        this.attemptRepo = attemptRepo;
        this.attemptService = attemptService;
        this.paperRepo = paperRepo;
        this.rn = new Random();
        this.loremIpsum = new LoremIpsum();
    }

    public void simulateExam(int examId, HttpSession session) {
        examService.validateStatus(examId, ExamStatus.CREATED);
        examService.validateUser(examId, session);
        examService.beginExam(examId);

        try {
            ExamModel model = examRepo.getExam(examId);
            simulateAttempts(model.getAttempts(), model.getPaperRef());
            examService.finnishExam(examId);

        } catch (AccessException e) {
            LOGGER.log(Level.WARNING, "Failed to simulate exam with id " + examId);
        }
    }

    private void simulateAttempts(List<AttemptReferenceModel> attempts, PaperReferenceModel paperReference) {
        try {
            PaperModel examPaper = paperRepo.getPaper(paperReference.getId(), paperReference.getVersionNo());

            for (AttemptReferenceModel attempt : attempts) {
                attemptRepo.setAttemptStatus(ExamStatus.STARTED,attempt.getId());
                for (SectionModel section : examPaper.getSections().values()) {
                    answerSection(attempt, section);
                }
                attemptRepo.setAttemptStatus(ExamStatus.FINISHED,attempt.getId());
            }

        } catch (AccessException e) {
            LOGGER.log(Level.WARNING, "Failed to simulate attempts.");
        }
    }

    private void answerSection(AttemptReferenceModel attempt, SectionModel sectionModel) {
        List<Integer> answeredQuestions = new ArrayList<>();
        int highestQuestionNumber = findMax(sectionModel.getQuestions().keySet());
        while (answeredQuestions.size() != sectionModel.getNoOfQuestionsToAnswer()) {
            int questionToAnswer = getRandomIntNotIn(answeredQuestions, 1, highestQuestionNumber);
            answerQuestion(sectionModel.getQuestions().get(questionToAnswer), attempt);
            answeredQuestions.add(questionToAnswer);
        }
    }

    private void answerQuestion(QuestionModel questionModel, AttemptReferenceModel attempt) {
        SubmitAnswerFormModel answer = new SubmitAnswerFormModel();
        answer.setAttemptId(attempt.getId());
        answer.setQuestionId(questionModel.getId());
        answer.setQuestionVersionNo(questionModel.getVersionNo());
        pickRandomAnswerForQuestion(questionModel,answer);
        attemptService.submitAnswer(answer, attempt.getCandidateName());
    }

    private void pickRandomAnswerForQuestion(QuestionModel questionModel, SubmitAnswerFormModel answer) {
        if (questionModel.getType().equals(QuestionType.ESSAY.toString())) {
            answer.setText(loremIpsum.getWords(getRandomIntBetween(10,150)));
        } else if (questionModel.getType().equals(QuestionType.CODE.toString())) {
            answer.setText(RANDOM_CODE);
        } else if (questionModel.getType().equals(QuestionType.DRAWING.toString())) {
            answer.setBase64imagePng(RANDOM_IMAGE_ENCODED);
        } else if (questionModel.getType().equals(QuestionType.MULTIPLE_CHOICE.toString())) {
            List<String> possibleAnswers = questionModel.getAnswerLetters();
            int pick = getRandomIntBetween(0, possibleAnswers.size() - 1);
            answer.setText(possibleAnswers.get(pick));
        }
    }

    private int findMax(Collection<Integer> numbers) {
        int max = 0;
        for (Integer number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    private int getRandomIntNotIn(Collection<Integer> notIn, int min, int max) {
        int generated = getRandomIntBetween(min,max);
        if (notIn.contains(generated)) {
            return getRandomIntNotIn(notIn, min, max);
        } else {
            return generated;
        }
    }

    private int getRandomIntBetween(int min, int max) {
        return rn.nextInt(max - min + 1) + min;
    }
}
