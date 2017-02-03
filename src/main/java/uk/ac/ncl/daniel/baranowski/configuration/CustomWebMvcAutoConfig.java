package uk.ac.ncl.daniel.baranowski.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class CustomWebMvcAutoConfig extends WebMvcAutoConfigurationAdapter {

    /**
     * This class tells Spring to look for its static resources in the base folder of the project.
     * Spring will first check if there is a static folder in the same folder as the jar file
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String myExternalFilePath = "static/";
        registry.addResourceHandler("/m/**").addResourceLocations(myExternalFilePath);
        registry.addResourceHandler("/static/config/**").addResourceLocations("/static/config/", "file:config/");
        super.addResourceHandlers(registry);
    }
}