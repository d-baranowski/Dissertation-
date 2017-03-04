package uk.ac.ncl.daniel.baranowski.data;

import uk.ac.ncl.daniel.baranowski.data.access.PaperDAO;
import uk.ac.ncl.daniel.baranowski.data.access.PaperVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionAssetDAO;
import uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.SectionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.SectionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TermsAndConditionsDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.*;
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
import static uk.ac.ncl.daniel.baranowski.data.mappers.AssetModelMapper.mapQuestionVersionAssetFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.PaperModelMapper.*;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionAssetsFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionVersionFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.SectionReferenceModelMapper.*;

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
                if (latestVersionNo != 0) {
                    result.add(QuestionModelMapper.mapQuestionReferenceModelFrom(
                            q,
                            latestVersionNo,
                            questionVersionDao.getTimeScale(q.getId(), latestVersionNo)));
                }
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to get all question references", e);
            throw new AccessException(e.getMessage());
        }

        return result;
    }

    public void removeQuestionFromSection(int questionId, int questionVersion, int sectionId, int sectionVersion) throws AccessException {
        try {
            questionVersionDao.deleteEntry(questionId, questionVersion, sectionId, sectionVersion);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to delete question version entry", e);
            throw new AccessException(e.getMessage());
        }
    }

    public void removeSectionFromPaper(int sectionId, int sectionVersion, int paperId, int paperVersion) throws AccessException {
        try {
            sectionVersionDao.deleteEntry(sectionId, sectionVersion, paperId, paperVersion);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to delete question version entry", e);
            throw new AccessException(e.getMessage());
        }
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
                    SectionVersion ver = sectionVersionDao.read(s.getId(), versionNo);
                    result.add(mapSectionReferenceModelFrom(ver, s.getReferenceName()));
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
                int versionNo = sectionVersionDao.getLatestVersionNo(s.getId());
                SectionVersion ver = sectionVersionDao.read(s.getId(), versionNo);
                result.add(mapSectionReferenceModelFrom(ver , s.getReferenceName()));
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

    public int createQuestion(QuestionModel model) throws AccessException {
        try {
            model.setVersionNo(1);
            int id = questionDao.create(mapQuestionFrom(model));
            model.setId(id);
            questionVersionDao.create(mapQuestionVersionFrom(model));
            mapQuestionAssetsFrom(model).forEach(assetModel -> {
                questionAssetDao.create(mapQuestionVersionAssetFrom(assetModel,id, 1));
            });
            return id;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to create question " + model, e);
            throw new AccessException("Failed to create question", e);
        }
    }

    public int updateQuestion(QuestionModel model) throws AccessException {
        try {
            boolean isUsed = questionVersionDao.checkIfVersionIsUsed(model.getId(),model.getVersionNo());
            if (isUsed) {
                int newVersionNo = model.getVersionNo() + 1;
                model.setVersionNo(newVersionNo);
                questionVersionDao.create(mapQuestionVersionFrom(model));
                return newVersionNo;
            } else {
                questionVersionDao.update(mapQuestionVersionFrom(model));
                return model.getVersionNo();
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to update question " + model, e);
            throw new AccessException("Failed to update question", e);
        }
    }

    public int createSection(SectionModel model) throws AccessException {
        try {
            model.setVersionNumber(1);
            int id = sectionDao.create(mapSectionFrom(model));
            model.setId(id);
            sectionVersionDao.create(mapSectionVersionFrom(model));
            return id;
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to create section " + model, e);
            throw new AccessException("Failed to create section", e);
        }
    }

    public int updateSection(SectionModel model) throws AccessException {
        try {
            boolean isUsed = sectionVersionDao.checkIfVersionIsUsed(model.getId(),model.getVersionNumber());
            if (isUsed) {
                int oldVersionNo = model.getVersionNumber();
                int newVersionNo = sectionVersionDao.getLatestVersionNo(model.getId()) + 1;
                model.setVersionNumber(newVersionNo);
                sectionVersionDao.create(mapSectionVersionFrom(model));

                questionVersionDao.copyEntries(model.getId(), oldVersionNo, newVersionNo);

                return newVersionNo;
            } else {
                sectionVersionDao.update(mapSectionVersionFrom(model));
                return model.getVersionNumber();
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to update section " + model, e);
            throw new AccessException("Failed to update section ", e);
        }
    }

    public int updatePaper(PaperModel model, String authorId) throws AccessException {
        try {
            boolean isUsed = paperVersionDao.checkIfVersionIsUsed(model.getId(),model.getVersionNo());
            if (isUsed) {
                int oldVersionNo = model.getVersionNo();
                int newVersionNo = paperVersionDao.getLatestVersionNo(model.getId()) + 1;
                model.setVersionNo(newVersionNo);
                paperVersionDao.create(mapPaperVersionFrom(model,authorId));

                sectionVersionDao.copyEntries(model.getId(), oldVersionNo, newVersionNo);

                return newVersionNo;
            } else {
                paperVersionDao.update(mapPaperVersionFrom(model,authorId));
                return model.getVersionNo();
            }
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to update paper " + model, e);
            throw new AccessException("Failed to update paper ", e);
        }
    }

    public SectionModel getSectionModel(int sectionId, int sectionVersionNo) throws AccessException {
        try {
            SectionVersion sectionVersion = sectionVersionDao.read(sectionId,sectionVersionNo);
            Section sectionParent = sectionDao.read(sectionId);
            return  mapSectionModelFrom(sectionParent, sectionVersion,
                    getSectionQuestions(sectionId, sectionVersionNo));
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, String.format("Failed to get section with id %s and versionNo %s",
                    sectionId, sectionVersionNo), e);
            throw new AccessException(e.getMessage());
        }
    }

    public void moveQuestion(int questionId, int questionVerNo, int sectionId, int sectionVersion, int newRef) throws AccessException {
        try {
            questionVersionDao.moveQuestion(questionId, questionVerNo, sectionId, sectionVersion, newRef);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to move question within section.");
            throw new AccessException(e.getMessage());
        }
    }

    public void moveSection(int sectionId, int sectionVer, int paperId, int paperVer, int newRef) throws AccessException {
        try {
            sectionVersionDao.moveSection(sectionId, sectionVer, paperId, paperVer, newRef);
        } catch (DataAccessException e) {
            LOGGER.log(Level.WARNING, "Failed to move question within section.");
            throw new AccessException(e.getMessage());
        }
    }

    public int addQuestionToSection(int questionId, int questionVersion, int sectionId, int sectionVersion) throws AccessException {
        try {
            QuestionVersionEntry entry = questionVersionDao.getEntry(questionId, questionVersion, sectionId, sectionVersion);

            //If entry already exists in the section
            if (!entry.equals(new QuestionVersionEntry())) {
                return -1;
            } else {
                int questionNumber = getLastQuestionNumber(sectionId, sectionVersion);
                questionVersionDao.addQuestionToSection(
                        new QuestionVersionEntry()
                                .setSectionVersionNo(sectionVersion)
                                .setSectionId(sectionId)
                                .setReferenceNumber(questionNumber + 1)
                                .setQuestionId(questionId)
                                .setQuestionVersionNumber(questionVersion)
                );
                return questionNumber + 1;
            }
        } catch (DataAccessException e) {
            final String msg = "Failed to add question to section";
            LOGGER.log(Level.WARNING, msg, e);
            throw new AccessException(msg, e);
        }
    }

    public int addSectionToSection(int paperId, int paperVersion, int sectionId, int sectionVersion) throws AccessException {
        try {
            SectionVersionEntry entry =  sectionVersionDao.getEntry(paperId, paperVersion, sectionId, sectionVersion);

            //If entry already exists in the paper
            if (!entry.equals(new SectionVersionEntry())) {
                return -1;
            } else {
                int sectionNumber = getLastSectionNumber(paperId, paperVersion);
                sectionVersionDao.addSectionToPaper(
                        new SectionVersionEntry()
                                .setPaperId(paperId)
                                .setPaperVersionNumber(paperVersion)
                                .setReferenceNumber(sectionNumber + 1)
                                .setSectionId(sectionId)
                                .setSectionVersionNo(sectionVersion)
                );
                return sectionNumber + 1;
            }
        } catch (DataAccessException e) {
            final String msg = "Failed to add section to paper";
            LOGGER.log(Level.WARNING, msg, e);
            throw new AccessException(msg, e);
        }
    }

    public int getLastQuestionNumber(int sectionId, int sectionVersion) throws AccessException {
        try {
            return questionVersionDao.getLastQuestionNumber(sectionId, sectionVersion);
        } catch(DataAccessException e) {
            final String msg = "Failed to get latest question number";
            LOGGER.log(Level.WARNING, msg, e);
            throw new AccessException(msg, e);
        } catch (NullPointerException e) {
            final String msg = "There are no questions in section";
            LOGGER.log(Level.INFO, msg);
            return 0;
        }
    }

    public int getLastSectionNumber(int paperId, int paperVersion) throws AccessException {
        try {
            return sectionVersionDao.getLastSectionNumber(paperId, paperVersion);
        } catch(DataAccessException e) {
            final String msg = "Failed to get latest section number";
            LOGGER.log(Level.WARNING, msg, e);
            throw new AccessException(msg, e);
        } catch (NullPointerException e) {
            final String msg = "There are no sections in paper";
            LOGGER.log(Level.INFO, msg);
            return 0;
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

    public Map<Integer, QuestionModel> getSectionQuestions(int sectionId, int sectionVersionNo) {
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

    public void moveQuestionByIndex(int from, int to, int sectionId, int sectionVersionNo) throws AccessException {
        try {
            questionVersionDao.moveQuestionByPosition(from, to, sectionId, sectionVersionNo);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to move question from " + from + " to " + to + " in section " + sectionId + ":" + sectionVersionNo;
            LOGGER.log(Level.WARNING, errorMsg,e);
            throw new AccessException(errorMsg);
        }
    }

    public void moveSectionByIndex(int from, int to, int paperId, int paperVersion) throws AccessException {
        try {
            sectionVersionDao.moveSectionByPosition(from, to, paperId, paperVersion);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to move section from " + from + " to " + to + " in section " + paperId + ":" + paperVersion;
            LOGGER.log(Level.WARNING, errorMsg,e);
            throw new AccessException(errorMsg);
        }
    }

    public int createPaper(PaperModel model, String authorId) throws AccessException {
        try {
            int id = paperDao.create(mapPaperFrom(model));
            model.setId(id);
            model.setVersionNo(1);
            paperVersionDao.create(mapPaperVersionFrom(model,authorId));
            return id;
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to create paper " + model;
            LOGGER.log(Level.WARNING, errorMsg,e);
            throw new AccessException(errorMsg);
        }
    }

    public String getAuthorId(int id, int versionNo) throws AccessException {
        try {
            return paperVersionDao.getAuthorId(id, versionNo);
        } catch (DataAccessException e) {
            final String errorMsg = "Failed to get author id for paper " + id + " " + versionNo;
            LOGGER.log(Level.WARNING, errorMsg,e);
            throw new AccessException(errorMsg);
        }
    }
}
