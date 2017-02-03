package uk.ac.ncl.daniel.baranowski.views;


import uk.ac.ncl.daniel.baranowski.common.Constants;
import org.springframework.web.servlet.ModelAndView;

public class GenericErrorPageViewModel {
    private final String errorMsg;
    private final String errorHeader;
    private final String imageSrc;

    public GenericErrorPageViewModel(String errorMsg, String errorHeader, String imageSrc) {
        this.errorMsg = errorMsg;
        this.errorHeader = errorHeader;
        this.imageSrc = imageSrc;
    }

    public ModelAndView getModelAndView() {
        return new ModelAndView(Constants.TEMPLATE_GENERIC_ERROR_PAGE)
                .addObject("errorMsg", errorMsg)
                .addObject("errorHeader", errorHeader)
                .addObject("imageSrc", imageSrc);
    }
}
