package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class CheckInRecord {
    private int recordId;
    private String studentId;
    private String dormId;
    private java.sql.Date checkInDate;
    private java.sql.Date checkOutDate;


}