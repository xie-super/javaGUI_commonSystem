package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class Dormitory {
    private String dormId;
    private String building;
    private String roomNumber;
    private int capacity;
    private int currentOccupancy;
    private String gender;

    // Getters and Setters
    // ToString, Equals, and HashCode methods
}