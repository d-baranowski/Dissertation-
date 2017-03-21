package uk.ac.ncl.daniel.baranowski.data.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
/**
 * Custom annotation which works like @Component etc. Created to distinguish between DAO and Repository objects
 * @author Daniel
 *
 */
public @interface DataAccessObject {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";
}
