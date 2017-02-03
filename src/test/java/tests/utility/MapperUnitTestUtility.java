package tests.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapperUnitTestUtility {
    private MapperUnitTestUtility() {
        //Hides implicit public constructor
    }

    public static boolean areAllFieldsDifferent(Object before, Object after, List<Field> exceptThese) throws IllegalAccessException {
        if (exceptThese == null) {
            exceptThese = new ArrayList<>();
        }

        Class clazz = before.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        if (clazz != after.getClass()) {
            return false;
        }

        for (Field field : fields) {
            boolean isFieldFinal = Modifier.isFinal(field.getModifiers()); //Final fields values are not expected to be changed
            if (!(exceptThese.contains(field) || isFieldFinal)) {
                field.setAccessible(true);
                final Object beforeValue = field.get(before);
                final Object afterValue = field.get(after);
                if (Objects.equals(beforeValue, afterValue)) {
                    System.out.println("Mapper did not set value to field: " + field.getName());
                    return false;
                }
            }
        }

        return true;
    }
}
