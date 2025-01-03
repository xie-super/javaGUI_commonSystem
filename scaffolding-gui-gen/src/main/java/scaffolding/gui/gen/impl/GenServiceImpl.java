package scaffolding.gui.gen.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import scaffolding.gui.common.constants.Constants;
import scaffolding.gui.dal.entity.Dormitory;
import scaffolding.gui.gen.entity.GenTable;
import scaffolding.gui.gen.scan.TableScan;
import scaffolding.gui.gen.utils.VelocityInitializer;
import scaffolding.gui.gen.utils.VelocityUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @Description:
 * @Author: 乐滨
 * @Date: 2025-01-03
 */
@Slf4j
public class GenServiceImpl {
    public String getPath(){
        return Objects.requireNonNull(getClass().getResource("")).getPath();
    }

    public void genAllEntities() throws Exception {
        List<GenTable> tableInfos = TableScan.getAllTableInfos();
        for (GenTable genTable : tableInfos) {
            genEntityFromGenTable(genTable);
        }
    }
    public void genEntityByTableName(String tableName) throws Exception {
        GenTable genTable = TableScan.getTableInfoByTableName(tableName);
        genEntityFromGenTable(genTable);
    }

    private void genEntityFromGenTable(GenTable genTable) throws Exception {
        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtils.prepareContext(Objects.requireNonNull(genTable));

        List<String> templates = VelocityUtils.getTemplateList();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                String currentDirectory = System.getProperty("user.dir");
                String targetDirectory = currentDirectory + File.separator + "scaffolding-gui-dal" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "scaffolding" + File.separator + "gui" + File.separator + "dal" + File.separator + "entity" + File.separator;
                File outputFile = new File(targetDirectory + genTable.getClassName() + ".java");
                try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(outputFile.toPath()), StandardCharsets.UTF_8)) {
                    writer.write(sw.toString());
                }
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + genTable.getTableName(), e);
            }
        }
    }

    public static void main(String[] args) {

        GenServiceImpl genService = new GenServiceImpl();
        try {
            genService.genAllEntities();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
