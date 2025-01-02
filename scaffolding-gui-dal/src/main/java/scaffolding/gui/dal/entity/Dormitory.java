package scaffolding.gui.dal.entity;

import lombok.Data;
import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class Dormitory {

    @FieldDescription(description = "宿舍 ID")
    private String dormId;

    @FieldDescription(description = "宿舍楼")
    private String building;

    @FieldDescription(description = "房间号")
    private String roomNumber;

    @FieldDescription(description = "房间容量")
    private int capacity;

    @FieldDescription(description = "当前入住人数")
    private int currentOccupancy;

    @FieldDescription(description = "性别限制")
    private String gender;

}
