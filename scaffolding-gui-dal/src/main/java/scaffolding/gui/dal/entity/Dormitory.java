package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class Dormitory {

    @FieldDescription("宿舍 ID")
    private String dormId;

    @FieldDescription("宿舍楼")
    private String building;

    @FieldDescription("房间号")
    private String roomNumber;

    @FieldDescription("房间容量")
    private int capacity;

    @FieldDescription("当前入住人数")
    private int currentOccupancy;

    @FieldDescription("性别限制")
    private String gender;

}
