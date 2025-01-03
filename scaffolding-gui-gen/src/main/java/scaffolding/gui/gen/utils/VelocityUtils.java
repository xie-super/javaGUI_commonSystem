package scaffolding.gui.gen.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import scaffolding.gui.gen.entity.GenTable;
import scaffolding.gui.gen.entity.GenTableColumn;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模板处理工具类
 * 
 * @author
 */
public class VelocityUtils
{
    public static List<String> getTemplateList()
    {

        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        return templates;
    }

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable)
    {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("packageName", "scaffolding.gui.dal");
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("entityComment",genTable.getTableComment() );
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));

        velocityContext.put("author", "lb");
        velocityContext.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        return velocityContext;
    }


    public static HashSet<String> getImportList(GenTable genTable)
    {
        List<GenTableColumn> columns = genTable.getColumns();
        HashSet<String> importList = new HashSet<String>();
        importList.add("java.io.Serializable");
        for (GenTableColumn column : columns)
        {
            if ("Date".equals(column.getJavaType()))
            {
                importList.add("java.util.Date");
            }
            else if ("BigDecimal".equals(column.getJavaType()))
            {
                importList.add("java.math.BigDecimal");
            }
        }
        importList.add("lombok.*");
        importList.add("scaffolding.gui.dal.annotation.FieldDescription");
        return importList;
    }

}
