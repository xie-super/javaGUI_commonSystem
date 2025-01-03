package scaffolding.gui.dal.entity;

import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 宿舍信息
 *
 * @author lb
 * @date 2025-01-03 13:33:28
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dormitory implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 宿舍编号 */
    @FieldDescription(name = "dormId",description = "宿舍编号")
    private String dormId;

    /** 楼宇名称 */
    @FieldDescription(name = "building",description = "楼宇名称")
    private String building;

    /** 房间号码 */
    @FieldDescription(name = "roomNumber",description = "房间号码")
    private String roomNumber;

    /** 容纳人数 */
    @FieldDescription(name = "capacity",description = "容纳人数")
    private Integer capacity;

    /** 当前入住人数 */
    @FieldDescription(name = "currentOccupancy",description = "当前入住人数")
    private Integer currentOccupancy;

    /** 性别限制 */
    @FieldDescription(name = "gender",description = "性别限制")
    private String gender;


}
