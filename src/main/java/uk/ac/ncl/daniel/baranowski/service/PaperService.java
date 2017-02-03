package uk.ac.ncl.daniel.baranowski.service;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.exceptions.TestPaperDoesNotExistException;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.views.TestLibraryViewModel;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

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

    @RequestMapping(ControllerEndpoints.PAPER_TEST_LIBRARY)
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

    public String getInstructionsText(int id, int versionNo) {
        try {
            return repo.getPaperInstructionsText(id, versionNo);
        } catch (AccessException e) {
            final String errorMsg = "Failed to get all paper instructions text";
            LOGGER.log(SEVERE, errorMsg, e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
