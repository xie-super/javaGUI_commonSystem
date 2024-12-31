package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class Inspection {
    private int inspectionId;
    private String dormId;
    private String inspector;
    private java.sql.Date inspectionDate;
    private String remarks;


}
