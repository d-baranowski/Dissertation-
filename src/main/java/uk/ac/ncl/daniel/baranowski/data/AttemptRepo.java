package uk.ac.ncl.daniel.baranowski.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.access.*;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.*;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.data.mappers.AssetModelMapper;
import uk.ac.ncl.daniel.baranowski.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.data.mappers.AnswerModelMapper.mapAnswerFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AnswerModelMapper.mapAnswerModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AttemptModelMapper.mapAttempt;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AttemptModelMapper.mapAttemptReferenceModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.MarkModelMapper.mapMarkModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.PaperModelMapper.mapPaperFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.TestDayModelMapper.mapTestDayModel;

@Repository
public class AttemptRepo {
    private final PaperRepo paperRepo;
    private final PaperDAO paperDao;
    private final MarkDAO markDao;
    private final AnswerDAO answerDao;
    private final AnswerAssetDAO assetDao;
    private final TestDayEntryDAO attemptDao;
    private final TestDayDAO dayDao;
    private final CandidateDAO candidateDao;
    private final TermsAndConditionsDAO termsAndConditionsDAO;
    private static final Logger LOGGER = Logger.getLogger(AttemptRepo.class.getName());

    //This should always be created using dependency injection
    @Autowired
    public AttemptRepo(PaperRepo paperRepo, MarkDAO markDao, AnswerDAO answerDao, AnswerAssetDAO assetDao, //NOSONAR
                       TestDayEntryDAO attemptDao, TestDayDAO dayDao, CandidateDAO candidateDao, PaperDAO paperDao,
                       TermsAndConditionsDAO termsAndConditionsDAO) {
        this.paperRepo = paperRepo;
        this.markDao = markDao;
        this.answerDao = answerDao;
        this.assetDao = assetDao;
        this.attemptDao = attemptDao;
        this.dayDao = dayDao;
        this.candidateDao = candidateDao;
        this.paperDao = paperDao;
        this.termsAndConditionsDAO = termsAndConditionsDAO;
    }

    public AttemptReferenceModel getAttemptReferenceModel(int id) throws AccessException {
        try {
            final TestDayEntry attempt = attemptDao.read(id);
            return mapAttemptReferenceModelFrom(
                    attempt,
                    dayDao.read(attempt.getTestDayId()),
                    candidateDao.read(attempt.getCandidateId()),
                    paperDao.read(attempt.getPaperId()));
        } catch (EmptyResultDataAccessException e) {
            final String errorMsg = String.format("Failed to get test attempt reference with id %s ", id);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public List<AttemptReferenceModel> getAllAttemptReferences() throws AccessException {
        final List<AttemptReferenceModel> result = new ArrayList<>();
        try {
            for (TestDayEntry attempt : attemptDao.readAll()) {
                result.add(mapAttemptReferenceModelFrom(
                        attempt,
                        dayDao.read(attempt.getTestDayId()),
                        candidateDao.read(attempt.getCandidateId()),
                        paperDao.read(attempt.getPaperId())));
            }

            return result;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get all attempt references";
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public List<AttemptReferenceModel> getAttemptReferencesByCandidate(int candidateId) throws AccessException {
        final List<AttemptReferenceModel> result = new ArrayList<>();

        try {
            final List<TestDayEntry> attempts = attemptDao.getByCandidateId(candidateId);

            for (TestDayEntry attempt : attempts) {
                result.add(
                        mapAttemptReferenceModelFrom(
                                attempt,
                                dayDao.read(attempt.getTestDayId()),
                                candidateDao.read(attempt.getCandidateId()),
                                paperDao.read(attempt.getPaperId())
                        )
                );
            }

            return result;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get all attempt references by candidate id " + candidateId;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg);
        }
    }

    public List<AttemptReferenceModel> getAttemptReferencesByDateLocation(String date, String location) throws AccessException {
        final List<AttemptReferenceModel> result = new ArrayList<>();

        try {
            final List<TestDayEntry> attempts = attemptDao.getByDateLocation(date, location);

            for (TestDayEntry attempt : attempts) {
                result.add(
                        mapAttemptReferenceModelFrom(
                                attempt,
                                dayDao.read(attempt.getTestDayId()),
                                candidateDao.read(attempt.getCandidateId()),
                                paperDao.read(attempt.getPaperId())
                        )
                );
            }

        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get all attempt references by date %s and location %s", date, location);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg);
        }

        return result;
    }

    public List<AttemptReferenceModel> getAttemptReferencesByStatus(ExamStatus status) throws AccessException{
        final List<AttemptReferenceModel> result = new ArrayList<>();

        try {
            final List<TestDayEntry> attempts = attemptDao.getByEntryStatus(status.name());

            for (TestDayEntry attempt : attempts) {
                result.add(
                        mapAttemptReferenceModelFrom(
                                attempt,
                                dayDao.read(attempt.getTestDayId()),
                                candidateDao.read(attempt.getCandidateId()),
                                paperDao.read(attempt.getPaperId())
                        )
                );
            }
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get all attempt references by entry status %s", status);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg);
        }

        return result;
    }

    public String getTermsById(int id){
        return termsAndConditionsDAO.getTermsById(id);
    }

    public AttemptModel getAttemptModel(int id) throws AccessException {
        try {
            final TestDayEntry attempt = attemptDao.read(id);
            final TestDay day = dayDao.read(attempt.getTestDayId());
            final Candidate candidate = candidateDao.read(attempt.getCandidateId());
            final PaperModel paper = paperRepo.getPaper(attempt.getPaperId(), attempt.getPaperVersionNo());
            final AnswersMapModel answerMap = getAnswersForAttempt(id, paper);

            return mapAttempt(attempt, day, candidate, paper, answerMap);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get attempt model with id " + id;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public CandidateModel createAndGetCandidate(CandidateModel create) throws AccessException {
        final CandidateModel result;
        try {
            result = mapCandidateModelFrom(candidateDao.createAndGet(mapCandidateFrom(create)));
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to submit a candidate: " + create;
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }

        return result;
    }

    public CandidateModel getCandidate(int id) throws AccessException {
        try {
            return mapCandidateModelFrom(candidateDao.read(id));
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get candidate by id %s ", id);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public List<CandidateModel> getAllCandidates() throws AccessException {
        final List<CandidateModel> result = new ArrayList<>();
        candidateDao.readAll().forEach(candidate -> result.add(mapCandidateModelFrom(candidate)));
        return result;
    }

    public void submitAnswer(AnswerModel answerModel, int attemptId, int questionId, int questionVersionNo) throws AccessException {
        Answer answer = mapAnswerFrom(answerModel, attemptId, questionId, questionVersionNo);
        try {
            answerDao.submitAnswer(answer);
            if (answerModel.getAsset() != null) {
                assetDao.submit(AssetModelMapper.mapAnswerAssetFrom(answerModel.getAsset(), questionId, questionVersionNo, attemptId));
            }
        } catch (DataAccessException e) {
            final String errorMsg = String.format(
                    "Failed to submit answer %s for attempt with id %s, for question id %s version no %s",
                    answerModel,
                    attemptId,
                    questionId,
                    questionVersionNo);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void markAnswer(int attemptId, int questionId, int questionVersionNo, int markId) throws AccessException {
        try {
            answerDao.markAnswer(attemptId,questionId,questionVersionNo,markId);
        } catch (DataAccessException e) {
            final String errorMsg = String.format(
                    "Failed to mark answer for attempt with id %s, for question id %s version no %s with markId: %s",
                    attemptId,questionId,questionVersionNo, markId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void lockAttemptForMarking(int attemptId, String userId) throws AccessException {
        try {
            attemptDao.setMarkerId(attemptId, userId);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to lock attempt %s for marking by user with id %s", attemptId,userId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void unlockAttemptForMarking(int attemptId) throws AccessException {
        try {
            attemptDao.setMarkerId(attemptId, null);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to unlock attempt %s", attemptId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public boolean isMarkingAttempt(int attemptId, String userId) throws AccessException {
        try {
            final String actualUserId = attemptDao.getMarkersId(attemptId);
            return Objects.equals(userId, actualUserId);
        } catch (EmptyResultDataAccessException e) {
            final String errorMsg = String.format("There is no markers id for attempt with id %s", attemptId);
            LOGGER.log(Level.FINE, errorMsg, e);
            return false;
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get marker id for attempt with id %s", attemptId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }


    }

    public String getIdOfUserMarking(int attemptId) throws AccessException {
        try {
            return attemptDao.getMarkersId(attemptId);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get marker id for attempt with id %s", attemptId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public AttemptReferenceModel createAndGet(CandidateModel candidate, TestDayModel day, PaperReferenceModel paper, String status, int terms, Integer timeAllowed) throws AccessException {
        try {
            TestDayEntry attempt = attemptDao.createAndGet(
                    new TestDayEntry.Builder()
                            .setTestDayId(dayDao.getOrCreate(day).getId())
                            .setPaperId(paper.getId())
                            .setPaperVersionNo(paper.getVersionNo())
                            .setCandidateId(candidate.getId())
                            .setStatus(status)
                            .setTermsAndConditions(terms)
                            .setTimeAllowed(timeAllowed)
                            .build());

            return mapAttemptReferenceModelFrom(
                    attempt,
                    mapTestDayModel(day, attempt.getTestDayId()),
                    mapCandidateFrom(candidate),
                    mapPaperFrom(paper));
        } catch (DataAccessException e) {
            final String errorMsg = String.format(
                    "Could not submit test attempt reference for candidate: %s \n" +
                            " Test Day: %s \n" +
                            " and Paper: %s \n", candidate, day, paper);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void setAttemptStatus(ExamStatus status, int id) throws AccessException {
        try {
            attemptDao.updateStatus(status.name(), id);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to set attempt with id %s to status %s", id, status);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public void setAttemptFinalMark(int id , int mark) throws AccessException {
        try {
            attemptDao.updateFinalMark(id, mark);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to set mark %s to attempt with id %s", mark, id);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg,e);
        }
    }

    public String getAttemptStatus(int attemptId) throws AccessException {
        try {
            final TestDayEntry attempt =  attemptDao.read(attemptId);
            return attempt.getStatus();
        } catch (EmptyResultDataAccessException e) {
            final String errorMsg = String.format("Attempt with id: %s doesn't exist", attemptId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get attempt status for attempt with id: %s ", attemptId);
            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg, e);
        }
    }

    public AnswersMapModel getAnswersForAttempt(int attemptId, PaperModel paper) throws AccessException {
        final AnswersMapModel result = new AnswersMapModel();
        final Map<Integer, SectionModel> sections = paper.getSections();

        for (Entry<Integer, SectionModel> sectionEntry : sections.entrySet()) {
            final Map<Integer, QuestionModel> questions = sectionEntry.getValue().getQuestions();
            for (Entry<Integer, QuestionModel> questionEntry : questions.entrySet()) {
                final QuestionModel question = questionEntry.getValue();
                final Answer answer = answerDao.getByQuestion(attemptId, question.getId(), question.getVersionNo());
                final AnswerModel answerModel = getAnswerModel(answer);

                result.put(sectionEntry.getKey(), questionEntry.getKey(), answerModel);
            }
        }

        return result;
    }

    private AnswerModel getAnswerModel(Answer answer) throws AccessException {
        MarkModel mark = null;
        if (answer != null) {
            try {
                if (answer.getMarkId() != null) {
                    //TODO attach marker to mark
                    mark = mapMarkModelFrom(markDao.read(answer.getMarkId()), null);
                }

                return mapAnswerModelFrom(answer, mark, getAnswerAsset(answer.getQuestionId(), answer.getQuestionVersionNo(), answer.getTestDayEntryId()));
            } catch (DataAccessException e) {
                final String errorMsg = String.format("Failed to retrieve answer model for: %s", answer);
                LOGGER.log(Level.WARNING, errorMsg, e);
                throw new AccessException(errorMsg,e);
            }

        } else {
            return null;
        }
    }

    private AssetModel getAnswerAsset(int questionId, int questionVersionNumber, int testDayEntryId) throws AccessException {
        AnswerAsset daoResult = null;
        try {
            daoResult = assetDao.getByAnswer(questionId, questionVersionNumber, testDayEntryId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE,
                    String.format("There are no assets for answer with for question from attempt %s with questionId %s and questionVersionNumber %s ",
                            testDayEntryId, questionId, questionVersionNumber), e);
        } catch (DataAccessException e) {
            final String errorMsg = String.format("Failed to get answer assets for question with id %s, version %s for test attempt  %s"
            , questionId, questionVersionNumber, testDayEntryId);

            LOGGER.log(Level.WARNING, errorMsg, e);
            throw new AccessException(errorMsg,e);
        }

        if (daoResult != null) {
            return AssetModelMapper.mapAssetModelFrom(assetDao.getByAnswer(questionId, questionVersionNumber, testDayEntryId));
        }
        return null;
    }
}
