package uk.ac.ncl.daniel.baranowski.views;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;

public class TestLibraryViewModel {
    private final ModelAndView mav;

    public TestLibraryViewModel( List<PaperReferenceModel> references) {
        List<List<String>> tableRows = new ArrayList<>();

        references.forEach(reference -> tableRows.add(
                Arrays.asList(
                        Integer.toString(reference.getId()),
                        Integer.toString(reference.getVersionNo()),
                        reference.getReferenceName(),
                        buildLink(reference.getId(), reference.getVersionNo())
                )
        ));
        this.mav = new ModelAndView("testLibrary")
                .addObject("dashboardContent", "testLibrary")
                .addObject("tableRows",tableRows);
    }

    private String buildLink(int id, int version) {
        return String.format("<a href=%s>View</a>",
                ControllerEndpoints.PAPER_PREFIX + ControllerEndpoints.PAPER_VIEW
                        .replace("{paperId}", Integer.toString(id))
                        .replace("{paperVersionNo}", Integer.toString(version)));
    }

    public ModelAndView getMav() {
        return mav;
    }
}
