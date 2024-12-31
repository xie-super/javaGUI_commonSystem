package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class Student {
    private String studentId;
    private String password;
    private String name;
    private String gender;
    private String department;
    private String phone;
    private String dormId;
    private int bedNumber;
    private java.sql.Date checkInDate;

    // Getters and Setters
    // ToString, Equals, and HashCode methods
}