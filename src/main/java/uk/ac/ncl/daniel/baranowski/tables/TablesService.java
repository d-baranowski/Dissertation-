package uk.ac.ncl.daniel.baranowski.tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.tables.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.aop.support.AopUtils.getTargetClass;

@Service
public class TablesService {
    private final ApplicationContext context;

    private static final Logger LOGGER = Logger.getLogger(TablesService.class.getName());

    @Autowired
    public TablesService(ApplicationContext context) {
        this.context = context;
    }

    public ModelAndView populateTable(ModelAndView mav, Class modelType) {
        Map<String, Object> map = context.getBeansWithAnnotation(TableRepo.class);

        Object foundRepo = findRepoByType(map, modelType);

        if (foundRepo != null) {
            Method getAllMethod = getTableMethod(foundRepo, modelType);
            try {
                List<Object> objects = (List<Object>) getAllMethod.invoke(foundRepo);
                String header = getAllMethod.getAnnotation(GetAllMethod.class).friendlyName();
                header = header.substring(0, 1).toUpperCase() + header.substring(1);
                mav.addObject("header",header);

                populateModelAndView(mav, objects);
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.log(Level.WARNING, "Failed to populate a dynamic table.", e);
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return mav;
    }

    private Object findRepoByType(Map<String, Object> map, Class modelType) {
        for (Object repo : map.values()) {
            Class[] models = getTargetClass(repo).getAnnotation(TableRepo.class).models();

            for (Class model : models) {
                if (model.equals(modelType)) {
                    return repo;
                }
            }
        }
        return null;
    }

    private Method getTableMethod(Object repo, Class modelType) {
        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(getTargetClass(repo).getDeclaredMethods()));

        for (Method method : allMethods) {
            if (method.isAnnotationPresent(GetAllMethod.class)) {
                if (method.getAnnotation(GetAllMethod.class).model().equals(modelType)){
                    return method;
                }
            }
        }

        return null;
    }

    private List<Map<String, String>> getTableData(List<Object> objects, List<String> columnNames, ModelAndView mav) {
        Class listType = objects.get(0).getClass();
        List<Map<String, String>> listOfValues = new ArrayList<>();
        Map<String, Method> columnNameToGetter = new HashMap<>();
        Map<Integer, String> tempColumnNames = new HashMap<>();

        for (Method method : listType.getMethods()) {
            if (method.isAnnotationPresent(ColumnGetter.class)) {
                String columnName = method.getAnnotation(ColumnGetter.class).name();
                int columnIndex = method.getAnnotation(ColumnGetter.class).order();
                columnNameToGetter.put(columnName, method);
                tempColumnNames.put(columnIndex, columnName);
            }
            if (method.isAnnotationPresent(ViewEndpoint.class)) {
                columnNameToGetter.put("VIEW_ENDPOINT",method);
                mav.addObject("includeView", true);
            }
            if (method.isAnnotationPresent(EditEndpoint.class)) {
                columnNameToGetter.put("EDIT_ENDPOINT",method);
                mav.addObject("includeEdit", true);
            }
        }

        columnNames.addAll(tempColumnNames.values());
        for (int i : tempColumnNames.keySet()) {
            columnNames.set(i, tempColumnNames.get(i));
        }

        for (Object object : objects) {
            Map<String, String> columnNameToValue = new HashMap<>();
            for (String columnName : columnNameToGetter.keySet()) {
                try {
                    columnNameToValue.put(columnName,
                            columnNameToGetter
                                    .get(columnName)
                                    .invoke(object).toString());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.log(Level.WARNING, "Failed to populate a dynamic table.");
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            listOfValues.add(columnNameToValue);
        }

        return listOfValues;
    }


    private ModelAndView populateModelAndView(ModelAndView mav, List<Object> objects) {
        List<String> columnNames = new ArrayList<>();
        List<Map<String, String>> tableData = getTableData(objects,columnNames, mav);

        mav.addObject("columnNames",columnNames);
        mav.addObject("tableData", tableData);

        return mav;
    }
}
