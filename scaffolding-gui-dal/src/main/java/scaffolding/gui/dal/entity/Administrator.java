package scaffolding.gui.dal.entity;

import lombok.Data;

@Data
public class Administrator {
    private String adminId;
    private String name;
    private String phone;
    private String email;
    private String password;

    // Getters and Setters
    // ToString, Equals, and HashCode methods
}
