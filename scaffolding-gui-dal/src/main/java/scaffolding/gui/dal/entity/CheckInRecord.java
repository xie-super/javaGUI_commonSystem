package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

import java.sql.Date;

@Data
public class CheckInRecord {
    @FieldDescription("记录 ID")
    private int recordId;

    @FieldDescription("学生 ID")
    private String studentId;

    @FieldDescription("宿舍 ID")
    private String dormId;

    @FieldDescription("")
    private Date checkInDate;

    @FieldDescription("退宿日期")
    private Date checkOutDate;


}