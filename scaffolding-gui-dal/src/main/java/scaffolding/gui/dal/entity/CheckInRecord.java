package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

import java.sql.Date;

@Data
public class CheckInRecord {
    @FieldDescription(description = "记录 ID")
    private int recordId;

    @FieldDescription(description = "学生 ID")
    private String studentId;

    @FieldDescription(description = "宿舍 ID")
    private String dormId;

    @FieldDescription(description = "")
    private Date checkInDate;

    @FieldDescription(description = "退宿日期")
    private Date checkOutDate;


}