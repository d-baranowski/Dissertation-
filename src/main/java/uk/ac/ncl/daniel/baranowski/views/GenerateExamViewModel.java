package uk.ac.ncl.daniel.baranowski.views;

import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.ModuleReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;

import java.util.List;

public class GenerateExamViewModel {
    private final ModelAndView mav = new ModelAndView(Constants.TEMPLATE_DASHBOARD);

    public GenerateExamViewModel(List<PaperReferenceModel> referenceModels, List<CandidateModel> candidateModels, List<ModuleReferenceModel> modules){
        mav.addObject("paperReferences", referenceModels);
        mav.addObject("previousCandidates", candidateModels);
        mav.addObject("formBody", new SetupExamFormModel());
        mav.addObject("modules", modules);
        mav.addObject("dashboardContent", "generateTest");
    }

    public GenerateExamViewModel appendErrors(List<FieldError> errors, SetupExamFormModel target) {
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
