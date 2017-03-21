package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import uk.ac.ncl.daniel.baranowski.models.AnswersMapModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;

public class AttemptModelMapper {
    private AttemptModelMapper() {
        //Hiding implicit public constructor
    }

    public static AttemptReferenceModel mapAttemptReferenceModelFrom(TestDayEntry attempt, TestDay day, Candidate candidate, Paper p, int paperVersionNo) {
        final AttemptReferenceModel result = new AttemptReferenceModel();
        result.setId(attempt.getId());
        result.setCandidate(CandidateModelMapper.mapCandidateModelFrom(candidate));
        result.setDate(day.getDate());
        result.setPaperRef(PaperModelMapper.mapPaperReferenceModelFrom(p,paperVersionNo));
        result.setStatus(attempt.getStatus());
        result.setFinalMark(attempt.getFinalMark());
        result.setExamId(attempt.getExamId());
        result.setPassword(attempt.getPassword());
        result.setLogin(attempt.getLogin());
        return result;
    }

    public static AttemptModel mapAttempt(TestDayEntry attempt, TestDay day, Candidate candidate, PaperModel paper,
                                          AnswersMapModel answerMap) {
        final AttemptModel result = new AttemptModel();
        result.setId(attempt.getId());
        result.setDate(day.getDate());
        result.setCandidate(CandidateModelMapper.mapCandidateModelFrom(candidate));
        result.setPaper(paper);
        result.setAnswerMap(answerMap);
        result.setStatus(attempt.getStatus());
        result.setFinalMark(attempt.getFinalMark());
        result.setExamId(attempt.getExamId());
        result.setPassword(attempt.getPassword());
        result.setLogin(attempt.getLogin());
        return result;
    }
}
