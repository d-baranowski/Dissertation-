package uk.ac.ncl.daniel.baranowski.views;

import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.common.Constants;

import java.util.List;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

public class GenerateTestViewModel {
    private final ModelAndView mav = new ModelAndView(Constants.TEMPLATE_DASHBOARD);

    public GenerateTestViewModel(List<PaperReferenceModel> referenceModels, List<CandidateModel> candidateModels){
        mav.addObject("paperReferences", referenceModels);
        mav.addObject("previousCandidates", candidateModels);
        mav.addObject("formBody", new SetupExamFormModel());
        mav.addObject("dashboardContent", "generateTest");
    }

    public GenerateTestViewModel appendErrors(List<FieldError> errors, SetupExamFormModel target) {
        if (errors != null) {
            errors.forEach(error -> {
                mav.addObject(error.getField().replace(".", "") + "Error", error.getDefaultMessage());
            });
        }

        if (target != null) {
            mav.addObject("formBody", target);
        }

        return this;
    }

    public ModelAndView getModelAndView() {
        return mav;
    }
}
