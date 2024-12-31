package scaffolding.gui.dal.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superxie
 */
public class AnnotationUtils {

    public static List<String> getEntityDescriptionList(Class<?> entityClass) {
        List<String> headerList = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldDescription.class)) {
                FieldDescription description = field.getAnnotation(FieldDescription.class);
                headerList.add(description.value());
            }
        }
        return headerList;
    }
}
