package uk.ac.ncl.daniel.baranowski.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    /**
     * This was used to enable the SQL console access to the h2 in memory database, that we use when not in prod configuration.
     * If you want to use it uncomment this code and comment out Security Configuration.
     */
    /*@Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console*//*");
        return registrationBean;
    }*/
}