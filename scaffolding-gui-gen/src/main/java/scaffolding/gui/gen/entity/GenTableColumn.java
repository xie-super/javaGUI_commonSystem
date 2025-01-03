package scaffolding.gui.gen.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author: 乐滨
 * @Date: 2025-01-02
 */
@Data
@Builder
public class GenTableColumn {
    private String columnName;
    private String columnComment;
    private String columnType;
    private String javaType;
    private String javaField;
    private Boolean isPk;
}
