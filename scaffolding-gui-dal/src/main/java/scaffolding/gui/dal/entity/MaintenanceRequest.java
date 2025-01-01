package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class MaintenanceRequest { 

    @FieldDescription("维修请求 ID")
    private int requestId;

    @FieldDescription("宿舍 ID")
    private String dormId;

    @FieldDescription("问题描述")
    private String issueDescription;

    @FieldDescription("请求日期")
    private java.sql.Date requestDate;

    @FieldDescription("维修状态")
    private String status;

    @FieldDescription("解决日期")
    private java.sql.Date resolvedDate;

}
