package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

import java.sql.Date;

@Data
public class Student {

    @FieldDescription("学号")
    private String studentId;

    @FieldDescription("密码")
    private String password;

    @FieldDescription("姓名")
    private String name;

    @FieldDescription("性别")
    private String gender;

    @FieldDescription("系别")
    private String department;

    @FieldDescription("电话")
    private String phone;

    @FieldDescription("宿舍号")
    private String dormId;

    @FieldDescription("床号")
    private int bedNumber;

    @FieldDescription("入住日期")
    private Date checkInDate;

    // Getters and Setters
    // ToString, Equals, and HashCode methods
}