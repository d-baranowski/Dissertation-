package uk.ac.ncl.daniel.baranowski.service;

import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.MarksRepo;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.NotLockedForMarkingException;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitMarkFormModel;
import uk.ac.ncl.daniel.baranowski.views.TestAttemptViewModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

@Service
public class MarkingService {
	private final MarksRepo marksRepo;
    private final AttemptRepo attemptRepo;
    private final UserRepo userRepo;
	private static final Logger LOGGER = Logger.getLogger(MarkingService.class.getName());

	@Autowired
	public MarkingService(MarksRepo marksRepo, AttemptRepo attemptRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.marksRepo = marksRepo;
        this.attemptRepo = attemptRepo;
    }

	public ModelAndView getMarkableViewForTestAttempt(int attemptId) {
        final AttemptModel model;
        final TestAttemptViewModel mav;
        try {
            model = attemptRepo.getAttemptModel(attemptId);
            mav = new TestAttemptViewModel(model);

        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Failed to get attempt from repository " + attemptId, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return mav.getModelAndView();
	}

    public void startMarkingAttempt(int attemptId,  HttpSession markerSession) {
        try {
            attemptRepo.setAttemptStatus(ExamStatus.MARKING_ONGOING, attemptId);
            attemptRepo.lockAttemptForMarking(attemptId, SessionUtility.getUserId(markerSession));
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Could not start marking attempt with id: attemptId " + attemptId, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void submitMark(SubmitMarkFormModel formBody, HttpSession markerSession) {
        final UserReferenceModel userRef = userRepo.getUserReference(SessionUtility.getUserId(markerSession));

        final MarkModel mark = new MarkModel();
        mark.setComment(formBody.getComment());
        mark.setMark(formBody.getMark());
        mark.setMarker(userRef);
        int markId;

        try {
            markId = marksRepo.submitMark(mark);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to save mark: $s", mark);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }

        try {
            attemptRepo.markAnswer(formBody.getTestAttemptId(), formBody.getQuestionId(), formBody.getQuestionVersionNo(), markId);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to save mark: $s", mark);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }
    }

    public void finishMarkingTestAttempt(int testAttemptId) {
        try {
            attemptRepo.setAttemptStatus(ExamStatus.MARKED, testAttemptId);
            final int sumOfMarks = marksRepo.getSumOfMarksForAttempt(testAttemptId);
            attemptRepo.setAttemptFinalMark(testAttemptId, sumOfMarks);
        } catch (AccessException e ) {
            final String errorMsg = "Failed to finish marking attempt wityh id: " + testAttemptId;
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }
    }

    public void unlockMarking(int testAttemptId) {
        try {
            attemptRepo.unlockAttemptForMarking(testAttemptId);
            attemptRepo.setAttemptStatus(ExamStatus.FINISHED, testAttemptId);
        } catch (AccessException e ) {
            final String errorMsg = String.format("Failed to unlock attempt with id %s from marking.", testAttemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }
    }

    public void validateUserIsMarking(int testAttemptId, String userId) {
        try {
            if (!attemptRepo.isMarkingAttempt(testAttemptId, userId)) {
                throw new NotLockedForMarkingException(
                        String.format("User with id %s has not locked attempt with id %s",
                                userId, testAttemptId),
                        testAttemptId, userId);
            }
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to get marker id for attempt with id %s", testAttemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }
    }

    public boolean userIsMarking(int testAttemptId, String userId) {
        try {
            return attemptRepo.isMarkingAttempt(testAttemptId, userId);
        } catch (AccessException e) {
            final String errorMsg = String.format("Failed to get marker id for attempt with id %s", testAttemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR , errorMsg);
        }
    }

    public boolean isInMarking(int testAttemptId) throws Exception {
        try {
            return attemptRepo.getAttemptStatus(testAttemptId).equals(ExamStatus.MARKING_ONGOING.name());
        } catch (AccessException e) {
            final String errorMsg = String.format("Could't get status for attempt with id %s", testAttemptId);
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new Exception(errorMsg, e);
        }
    }
}
