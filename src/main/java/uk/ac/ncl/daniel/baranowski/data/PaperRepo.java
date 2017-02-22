package uk.ac.ncl.daniel.baranowski.data;

import uk.ac.ncl.daniel.baranowski.data.access.PaperDAO;
import uk.ac.ncl.daniel.baranowski.data.access.PaperVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionAssetDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.SectionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.SectionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper;

import static uk.ac.ncl.daniel.baranowski.data.mappers.AssetModelMapper.mapAssetModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.PaperModelMapper.mapPaperModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.PaperModelMapper.mapPaperReferenceModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.SectionReferenceModelMapper.mapSectionModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.SectionReferenceModelMapper.mapSectionReferenceModelFrom;

@Repository
public class PaperRepo {
    private final SectionDAO sectionDao;
    private final SectionVersionDAO sectionVersionDao;
    private final QuestionDAO questionDao;
    private final QuestionVersionAssetDAO questionAssetDao;
    private final PaperVersionDAO paperVersionDao;
    private final PaperDAO paperDao;
    private final QuestionVersionDAO questionVersionDao;
    private final TermsAndConditionsDAO termsAndConditionsDAO;
    private static final Logger LOGGER = Logger.getLogger(PaperRepo.class.getName());

    @Autowired
    public PaperRepo(SectionDAO sectionDao, SectionVersionDAO sectionVersionDao, QuestionDAO questionDao,
                     QuestionVersionAssetDAO questionAssetDao, PaperVersionDAO paperVersionDao, PaperDAO paperDao,
                     QuestionVersionDAO questionVersionDao, TermsAndConditionsDAO termsAndConditionsDAO) {
        this.sectionDao = sectionDao;
        this.sectionVersionDao = sectionVersionDao;
        this.questionDao = questionDao;
        this.questionAssetDao = questionAssetDao;
        this.paperVersionDao = paperVersionDao;
        this.paperDao = paperDao;
        this.questionVersionDao = questionVersionDao;
        this.termsAndConditionsDAO = termsAndConditionsDAO;
    }

    public List<QuestionReferenceModel> getAllQuestionReferences() throws AccessException {
        final List<QuestionReferenceModel> result = new ArrayList<>();

        try {
            for (Question q : questionDao.readAll()) {
                for (Integer versionNo : questionVersionDao.getVersionNumbersById(q.getId())) {
                    result.add(QuestionModelMapper.mapQuestionReferenceModelFrom(q, versionNo, questionVersionDao.getTimeScale(q.getId(), versionNo)));
                }
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get all question references", e);
            throw new AccessException("Failed to get all question references", e);
        }

        return result;
    }

    public List<QuestionReferenceModel> getQuestionReferencesToLatestVersions() throws AccessException {
        final List<QuestionReferenceModel> result = new ArrayList<>();
        try {
            for (Question q : questionDao.readAll()) {
                final int latestVersionNo = questionVersionDao.getLatestVersionNo(q.getId());
                result.add(QuestionModelMapper.mapQuestionReferenceModelFrom(
                        q,
                        latestVersionNo,
                        questionVersionDao.getTimeScale(latestVersionNo, q.getId())));
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get all question references", e);
            throw new AccessException(e.getMessage());
        }

        return result;
    }

    public QuestionModel getQuestionById(int questionId, int versionNo) throws AccessException {
        try {
            Question q = questionDao.read(questionId);
            QuestionVersion qv = questionVersionDao.read(questionId, versionNo);
            List<QuestionVersionAsset> assets = questionAssetDao.getByQuestionVersion(questionId, versionNo);

            return QuestionModelMapper.mapQuestionModelFrom(q, qv, buildQuestionAssetList(assets));
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING,
                    String.format("Failed to question with id %s and version no %s", questionId, versionNo), e);
            throw new AccessException(e.getMessage());
        }
    }

    public List<SectionReferenceModel> getAllSectionReferences() throws AccessException {
        try {
            List<SectionReferenceModel> result = new ArrayList<>();

            for (Section s : sectionDao.readAll()) {
                for (Integer versionNo : sectionVersionDao.getVersionNumbersById(s.getId())) {
                    result.add(mapSectionReferenceModelFrom(s, versionNo));
                }
            }

            return result;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get all section references", e);
            throw new AccessException(e.getMessage());
        }
    }

    public List<SectionReferenceModel> getSectionReferencesToLatestVersions() throws AccessException {
        try {
            List<SectionReferenceModel> result = new ArrayList<>();

            for (Section s : sectionDao.readAll()) {
                result.add(mapSectionReferenceModelFrom(s, sectionVersionDao.getLatestVersionNo(s.getId())));
            }

            return result;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get section references to latest versions", e);
            throw new AccessException(e.getMessage());
        }
    }

    public PaperReferenceModel getPaperReference(int paperId, int versionNo) throws AccessException {
        try {
            Paper p = paperDao.read(paperId);
            PaperVersion pv = paperVersionDao.read(paperId, versionNo);
            return mapPaperReferenceModelFrom(p, pv.getVersionNo());
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING,
                    String.format("Failed to get paper reference for id: %s versionNo: %s", paperId, versionNo), e);
            throw new AccessException(
                    String.format("Failed to get paper reference for id: %s versionNo: %s", paperId, versionNo), e);
        }
    }

    public PaperReferenceModel getLatestPaperReference(int paperId) throws AccessException {
        return getPaperReference(paperId,getLatestVersionNo(paperId));
    }

    public List<PaperReferenceModel> getAllPaperReferences() throws AccessException {
        try {
            List<PaperReferenceModel> result = new ArrayList<>();

            for (Paper p : paperDao.readAll()) {
                for (Integer versionNo : paperVersionDao.getVersionNumbersById(p.getId())) {
                    result.add(mapPaperReferenceModelFrom(p, versionNo));
                }
            }

            return result;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get all paper references", e);
            throw new AccessException(e.getMessage());
        }
    }

    public List<PaperReferenceModel> getPaperReferencesToLatestVersions() throws AccessException {
        try {
            List<PaperReferenceModel> result = new ArrayList<>();

            for (Paper p : paperDao.readAll()) {
                int versionNo = paperVersionDao.getLatestVersionNo(p.getId());
                result.add(mapPaperReferenceModelFrom(p, versionNo));
            }

            return result;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get paper references to latest versions", e);
            throw new AccessException(e.getMessage());
        }
    }

    public int getLatestVersionNo(int paperId) {
        return paperVersionDao.getLatestVersionNo(paperId);
    }

    public PaperModel getPaper(int id, int versionNo) throws AccessException {
        try {
            Paper p = paperDao.read(id);
            PaperVersion pv = paperVersionDao.read(id, versionNo);
            return mapPaperModelFrom(p, pv, getPaperSections(id, versionNo));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE, String.format("Paper with id %s and version %s does not exist.", id, versionNo), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, String.format("Failed to get paper with id %s and versionNo %s", id, versionNo), e);
            throw new AccessException(e.getMessage());
        }
    }

    public void setTermsAndConditions(String terms){
        termsAndConditionsDAO.createNewTermsAndConditions(terms);
    }

    public String getPaperInstructionsText(int id, int versionNo) throws AccessException {
        try {
            return paperVersionDao.getInstructionsText(id, versionNo);
        } catch (DataAccessException e) {
            throw new AccessException(String.format("Failed to get instructions text for paper with id: %s and version number %s", id, versionNo) ,e);
        }
    }

    public int calculatePaperTimeScale(int paperId, int versionNo) throws AccessException {
        try {
            List<SectionVersion> sections = sectionVersionDao.getByPaperVersionSections(paperId, versionNo);
            return sections.stream().mapToInt(SectionVersion::getTimeScale).sum();
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, String.format("Failed to get paper time scale for paper with id %s and versionNo %s",
                    paperId, versionNo), e);
            throw new AccessException(String.format("Failed to get paper time scale for paper with id %s and versionNo %s",
                    paperId, versionNo), e);
        }
    }

    private Map<Integer, SectionModel> getPaperSections(int paperId, int versionNo) throws AccessException {
        try {
            Map<Integer, SectionModel> result = new HashMap<>();
            List<SectionVersion> sections = sectionVersionDao.getByPaperVersionSections(paperId, versionNo);

            sections.forEach(sectionVersion -> {
                Section sectionParent = sectionDao.read(sectionVersion.getSectionId());
                Integer referenceNumber = sectionVersionDao.getSectionReferenceNumber(sectionVersion.getSectionId(),
                        sectionVersion.getVersionNo(), paperId, versionNo);
                result.put(referenceNumber, mapSectionModelFrom(sectionParent, sectionVersion,
                        getSectionQuestions(sectionVersion.getSectionId(), sectionVersion.getVersionNo())));
            });

            return result;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, String.format("Failed to get paper sections for paper with id %s and versionNo %s",
                    paperId, versionNo), e);
            throw new AccessException(e.getMessage());
        }
    }

    private Map<Integer, QuestionModel> getSectionQuestions(int sectionId, int sectionVersionNo) {
        Map<Integer, QuestionModel> result = new HashMap<>();
        List<QuestionVersion> questionVersions = questionVersionDao.getBySection(sectionId, sectionVersionNo);

        questionVersions.forEach(gv -> {
            Question q = questionDao.read(gv.getQuestionId());
            List<QuestionVersionAsset> assets = questionAssetDao.getByQuestionVersion(gv.getQuestionId(),
                    gv.getVersionNo());
            Integer referenceNumber = questionVersionDao.getQuestionReferenceNumber(sectionId, sectionVersionNo,
                    gv.getQuestionId(), gv.getVersionNo());
            result.put(referenceNumber, QuestionModelMapper.mapQuestionModelFrom(q, gv, buildQuestionAssetList(assets)));
        });

        return result;
    }

    private List<AssetModel> buildQuestionAssetList(List<QuestionVersionAsset> qa) {
        final List<AssetModel> result = new ArrayList<>();
        qa.forEach(a -> result.add(mapAssetModelFrom(a)));
        return result;
    }
}
