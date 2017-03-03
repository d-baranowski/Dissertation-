package uk.ac.ncl.daniel.baranowski.service;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.*;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.models.api.AddQuestionToSection;
import uk.ac.ncl.daniel.baranowski.models.api.MoveQuestionInSection;
import uk.ac.ncl.daniel.baranowski.models.api.RemoveQuestionFromSection;
import uk.ac.ncl.daniel.baranowski.views.TestLibraryViewModel;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

@Service
public class PaperService {
    private final PaperRepo repo;
    private static final Logger LOGGER = Logger.getLogger(PaperService.class.getName());

    @Autowired
    private PaperService(PaperRepo repo) { //NOSONAR Left private to enforce dependency injection using Autowired
        this.repo = repo;
    }

    public ModelAndView getViewPaperModelAndView(int id, int versionNo) {
        PaperModel paper;
        try {
            paper = repo.getPaper(id, versionNo);

            if (paper != null) {
                ModelAndView mav = new ModelAndView("paper");
                mav.addObject("paper", paper);
                mav.addObject("answerable", false);
                mav.addObject("dashboardContent", "testLibrary");
                mav.addObject("inMarking", false);
                return mav;
            } else {
                throw new TestPaperDoesNotExistException(String.format("Paper with id: %s and Version number: %s does not exist.", id, versionNo));
            }
        } catch (AccessException e) {
            LOGGER.log(WARNING, String.format("Failed to get paper with id %s and version no %s", id, versionNo), e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ModelAndView getViewTestLibrary() {
        try {
            List<PaperReferenceModel> references = repo.getAllPaperReferences();
            return new TestLibraryViewModel(references).getMav();
        } catch (AccessException e) {
            final String errorMsg = "Failed to get all paper references";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ModelAndView getViewQuestion(int questionId, int questionVersion) {
        ModelAndView mav = new ModelAndView("viewQuestion");
        try {
            QuestionModel model =repo.getQuestionById(questionId, questionVersion);
            mav.addObject("currentQuestion",model);
            mav.addObject("sectionKey", model.getReferenceName());
            mav.addObject("questionKey", "Version: " + model.getVersionNo());

        } catch (AccessException e) {
            final String errorMsg = "Failed to get question with id " + questionId + " and version " + questionVersion;
            LOGGER.log(SEVERE, errorMsg, e);
            throw new QuestionMissingException(errorMsg);
        }

        return mav;
    }

    public ModelAndView getSectionEditor(int sectionId, int sectionVersion) {
        ModelAndView mav = new ModelAndView("sectionEditor");
        try {
            mav.addObject("questions",repo.getQuestionReferencesToLatestVersions());
        } catch (AccessException e) {
            final String errorMsg = "Failed to get all paper references.";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        mav.addObject("ENDPOINT", PAPER_PREFIX + PAPER_CREATE_SECTION);
        mav.addObject("UPDATE_ENDPOINT", PAPER_PREFIX + PAPER_UPDATE_SECTION);
        mav.addObject("ADD_QUESTION_ENDPOINT", PAPER_PREFIX + PAPER_ADD_QUESTION_TO_SECTION);
        if (sectionId != 0 && sectionVersion != 0) {
            try {
                mav.addObject("formObject", repo.getSectionModel(sectionId, sectionVersion));
            } catch (AccessException e) {
                final String errorMsg = "Failed specified section. Adding an empty section instead.";
                LOGGER.log(SEVERE, errorMsg, e);
                mav.addObject("formObject", new SectionModel());
            }
        } else {
            mav.addObject("formObject", new SectionModel());
        }

        return mav;
    }

    public ModelAndView getSectionQuestionsTableBody(int sectionId, int sectionVersion) {
        ModelAndView mav = new ModelAndView("fragments/author/sectionEditor/sectionQuestionsTableBody");
        mav.addObject("sectionQuestionsTableList", repo.getSectionQuestions(sectionId,sectionVersion));
        return mav;
    }

    public String getInstructionsText(int id, int versionNo) {
        try {
            return repo.getPaperInstructionsText(id, versionNo);
        } catch (AccessException e) {
            final String errorMsg = "Failed to get all paper instructions text";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public int createQuestion(QuestionModel model) {
        try {
            model.setText(sanitizeHTML(model.getText()));
            model.setMarkingGuide(sanitizeHTML(model.getMarkingGuide()));
            return repo.createQuestion(model);
        } catch (AccessException e) {
            final String errorMsg = "Failed to create question " + model;
            LOGGER.log(SEVERE, errorMsg, e);
            return -1;
        }
    }

    public int updateQuestion(QuestionModel model) {
        try {
            model.setText(sanitizeHTML(model.getText()));
            model.setMarkingGuide(sanitizeHTML(model.getMarkingGuide()));
            return repo.updateQuestion(model);
        } catch (AccessException e) {
            final String errorMsg = "Failed to update question " + model;
            LOGGER.log(SEVERE, errorMsg, e);
            return -1;
        }
    }

    public int createSection(SectionModel model) {
        try {
            model.setInstructionsText(sanitizeHTML(model.getInstructionsText()));
            return repo.createSection(model);
        } catch (AccessException e) {
            final String errorMsg = "Failed to create question " + model;
            LOGGER.log(SEVERE, errorMsg, e);
            return -1;
        }
    }

    public int updateSection(SectionModel model) {
        try {
            model.setInstructionsText(sanitizeHTML(model.getInstructionsText()));
            return repo.updateSection(model);
        } catch (AccessException e) {
            final String errorMsg = "Failed to update section" + model;
            LOGGER.log(SEVERE, errorMsg, e);
            return -1;
        }
    }

    public int addQuestionToSection(AddQuestionToSection q) throws FailedToAddQuestionToSectionException {
        try {
            return repo.addQuestionToSection(q.getQuestionId(), q.getQuestionVersion(), q.getSectionId(), q.getSectionVersion());
        }  catch (AccessException e) {
            final String errorMsg = "Add question to section";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new FailedToAddQuestionToSectionException(errorMsg);
        }
    }

    public void removeQuestionFromSection(RemoveQuestionFromSection q) throws FailedToMoveQuestionWithinSectionException, FailedToRemoveQuestionFromSectionException {
        try {
            repo.removeQuestionFromSection(q.getQuestionId(), q.getQuestionVersion(), q.getSectionId(), q.getSectionVersion());
            int lastIndex = repo.getLastQuestionNumber(q.getSectionId(), q.getSectionVersion());
            if (lastIndex != q.getQuestionNo()) {
                moveQuestionsUpToDeleted(q.getQuestionNo(), lastIndex, q.getSectionId(), q.getSectionVersion());
            }
        }  catch (AccessException e) {
            final String errorMsg = "Failed to remove question from section";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new FailedToRemoveQuestionFromSectionException(errorMsg);
        }
    }

    private void moveQuestionsUpToDeleted(int deletedNo, int lastNo, int sectionId, int sectionVersionNo) throws FailedToMoveQuestionWithinSectionException {
        try {
            for (int i = deletedNo; i <= lastNo; i++) {
                repo.moveQuestionByIndex(i + 1, i, sectionId, sectionVersionNo);
            }
        } catch (AccessException e){
            final String errorMsg = "Failed to move questions deleting";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new FailedToMoveQuestionWithinSectionException(errorMsg);
        }

    }

    public void moveQuestionWithinSection(MoveQuestionInSection[] q) throws FailedToMoveQuestionWithinSectionException {
        try {
            for (MoveQuestionInSection move : q) {
                repo.moveQuestion(
                        move.getQuestionId(),
                        move.getQuestionVerNo(),
                        move.getSectionId(),
                        move.getSectionVer(),
                        move.getNewRef());
            }
        } catch (AccessException e) {
            final String errorMsg = "Failed to move question within section ";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new FailedToMoveQuestionWithinSectionException(errorMsg);
        }
    }


    private String sanitizeHTML(String untrustedHTML) {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowAttributes("src").onElements("img")
                .allowAttributes("href").onElements("a")
                .allowStandardUrlProtocols()
                .allowCommonBlockElements()
                .allowCommonInlineFormattingElements()
                .allowStyling()
                .allowElements(
                        "a", "abbr", "address", "area", "article", "aside", "b", "base", "bdi", "bdo", "blockquote", "br", "button", "caption", "cite", "code", "col", "colgroup", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "header", "hgroup", "hr", "i", "img", "ins", "kbd", "keygen", "label", "legend", "li", "main", "map", "mark", "menu", "menuitem", "meter", "nav", "object", "ol", "optgroup", "option", "output", "p", "param", "pre", "progress", "queue", "rp", "rt", "ruby", "s", "samp", "style", "section", "select", "small", "source", "span", "strike", "strong", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "wbr"
                ).toFactory();

        return policy.sanitize(untrustedHTML);
    }
}
