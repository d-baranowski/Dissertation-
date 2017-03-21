package uk.ac.ncl.daniel.baranowski.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.annotation.PostConstruct;

@Configuration
public class ExternalTemplateConfiguration {
    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * This class is used to set settings to the thymeleaf template resolver. It specifies their location in
     * the project, as well as their suffix
     */
    @PostConstruct
    public void extension() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false);
        templateEngine.addTemplateResolver(resolver);
    }
}
