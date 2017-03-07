package uk.ac.ncl.daniel.baranowski.views;

import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitMarkFormModel;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;

public class TestAttemptViewModel {
	private final AttemptModel testAttemptModel;
	private final ModelAndView mav = new ModelAndView("paper");

	public TestAttemptViewModel(AttemptModel testAttemptModel) {
		this.testAttemptModel = testAttemptModel;
        mav.addObject("paper", testAttemptModel.getPaper());
        mav.addObject("answerable", isAnswerable());
        mav.addObject("inMarking", isInMarking());
        mav.addObject("examMarking", false);
        mav.addObject("attemptId", testAttemptModel.getId());
        mav.addObject("answers", testAttemptModel.getAnswerMap());
        mav.addObject("candidateName", testAttemptModel.getCandidate().getFullName());
        mav.addObject("totalQuestionsToAnswer", testAttemptModel.getPaper().getTotalQuestionsToAnswer());
		if (isAnswerable()) {
			mav.addObject("submitAnswerForm", new SubmitAnswerFormModel());
		}
        if (isInMarking()) {
            mav.addObject("submitMarkForm", new SubmitMarkFormModel());
	        mav.addObject("takenOnDate", testAttemptModel.getDate());
        }
	}

	public ModelAndView getModelAndView() {
		return mav;
	}

	public void hideNavigationBar() {
		mav.addObject("noNavigation", true);
	}

	private boolean isAnswerable() {
		return testAttemptModel.getStatus().equals(ExamStatus.STARTED.toString());
	}

	private boolean isInMarking() {
		return testAttemptModel.getStatus().equals(ExamStatus.MARKING_ONGOING.toString());
	}
}
