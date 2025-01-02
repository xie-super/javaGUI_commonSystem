package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

import java.sql.Date;

@Data
public class Student {

    @FieldDescription(description = "学号")
    private String studentId;

    @FieldDescription(description = "密码")
    private String password;

    @FieldDescription(description = "姓名")
    private String name;

    @FieldDescription(description = "性别")
    private String gender;

    @FieldDescription(description = "系别")
    private String department;

    @FieldDescription(description = "电话")
    private String phone;

    @FieldDescription(description = "宿舍号")
    private String dormId;

    @FieldDescription(description = "床号")
    private int bedNumber;

    @FieldDescription(description = "入住日期")
    private Date checkInDate;

    // Getters and Setters
    // ToString, Equals, and HashCode methods
}