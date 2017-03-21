package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.tables.TablesService;
import uk.ac.ncl.daniel.baranowski.tables.annotations.TableRepo;

import java.util.Map;

@Controller
@RequestMapping("view-all")
@PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author','ModuleLeader')")
public class ViewAllController {
    private final TablesService service;
    private final ApplicationContext context;

    @Autowired
    public ViewAllController(TablesService service,ApplicationContext context) {
        this.service = service;
        this.context = context;
    }

    @RequestMapping("/{modelName}")
    public ModelAndView viewAll(@PathVariable String modelName) {
        Class model = findClassByName(modelName);
        if (model == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
        ModelAndView mav = new ModelAndView("viewList");
        return service.populateTable(mav, model);
    }

    private Class findClassByName(String name) {
        Map<String, Object> beans = context.getBeansWithAnnotation(TableRepo.class);
        for (Object bean : beans.values()) {
            TableRepo annotation = AopUtils.getTargetClass(bean).getAnnotation(TableRepo.class);
            String [] names = annotation.friendlyNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(name)) {
                    return annotation.models()[i];
                }
            }

        }
        return null;
    }
}
