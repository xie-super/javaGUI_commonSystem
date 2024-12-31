package scaffolding.gui.dal.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author superxie
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDescription {
    String value();
}
