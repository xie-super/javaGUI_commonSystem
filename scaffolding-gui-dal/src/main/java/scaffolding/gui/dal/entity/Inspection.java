package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;


@Data
public class Inspection {

    @FieldDescription(description = "检查记录 ID")
    private int inspectionId;

    @FieldDescription(description = "宿舍 ID")
    private String dormId;

    @FieldDescription(description = "检查员")
    private String inspector;

    @FieldDescription(description = "检查日期")
    private java.sql.Date inspectionDate;

    @FieldDescription(description = "备注")
    private String remarks;

}
