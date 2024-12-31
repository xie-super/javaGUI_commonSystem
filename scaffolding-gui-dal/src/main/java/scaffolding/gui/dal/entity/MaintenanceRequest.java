package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class MaintenanceRequest {
    private int requestId;
    private String dormId;
    private String issueDescription;
    private java.sql.Date requestDate;
    private String status;
    private java.sql.Date resolvedDate;


}