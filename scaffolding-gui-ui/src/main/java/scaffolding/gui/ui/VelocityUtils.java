package scaffolding.gui.ui;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class VelocityUtils {
    public static List<String> getTemplateList()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        return templates;
    }
}
