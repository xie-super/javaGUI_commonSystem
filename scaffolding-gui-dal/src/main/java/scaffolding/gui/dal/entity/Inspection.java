package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;


@Data
public class Inspection {

    @FieldDescription("检查记录 ID")
    private int inspectionId;

    @FieldDescription("宿舍 ID")
    private String dormId;

    @FieldDescription("检查员")
    private String inspector;

    @FieldDescription("检查日期")
    private java.sql.Date inspectionDate;

    @FieldDescription("备注")
    private String remarks;

}
