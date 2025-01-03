package scaffolding.gui.gen.impl;

import com.mysql.cj.xdevapi.Table;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import scaffolding.gui.common.constants.Constants;
import scaffolding.gui.gen.entity.GenTable;
import scaffolding.gui.gen.scan.TableScan;
import scaffolding.gui.gen.utils.VelocityInitializer;
import scaffolding.gui.gen.utils.VelocityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;

/**
 * @Description:
 * @Author: 乐滨
 * @Date: 2025-01-03
 */
public class GenServiceImpl {

    public void genEntityByTableName(String tableName) throws Exception {

        GenTable genTable = TableScan.getTableInfoByTableName(tableName);
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(Objects.requireNonNull(genTable));

        List<String> templates = VelocityUtils.getTemplateList();
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
            File outputFile = new File(outputDirPath, VelocityUtils.getFileName(template, genTable));

            try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(outputFile.toPath()), StandardCharsets.UTF_8)) {
                writer.write(sw.toString());
            }
        } catch (IOException e) {
            log.error("渲染模板失败，表名：" + genTable.getTableName(), e);
        }
        }

    }
}
