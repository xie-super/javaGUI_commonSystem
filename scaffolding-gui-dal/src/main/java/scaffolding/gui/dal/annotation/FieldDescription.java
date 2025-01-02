package scaffolding.gui.dal.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lb
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDescription {
    String name();
    String description();
}
