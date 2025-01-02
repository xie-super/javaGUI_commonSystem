package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class MaintenanceRequest { 

    @FieldDescription(description = "维修请求 ID")
    private int requestId;

    @FieldDescription(description = "宿舍 ID")
    private String dormId;

    @FieldDescription(description = "问题描述")
    private String issueDescription;

    @FieldDescription(description = "请求日期")
    private java.sql.Date requestDate;

    @FieldDescription(description = "维修状态")
    private String status;

    @FieldDescription(description = "解决日期")
    private java.sql.Date resolvedDate;

}
