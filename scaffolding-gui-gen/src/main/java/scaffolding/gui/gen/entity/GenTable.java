package scaffolding.gui.gen.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author superxie
 * @date 2025/1/2
 * @description
 */
@Data
@Builder
public class GenTable {
    private String tableName;
    private String tableComment;
    private String className;
    private List<GenTableColumn> columns;

}
