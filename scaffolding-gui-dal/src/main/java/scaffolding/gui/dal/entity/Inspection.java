package scaffolding.gui.dal.entity;

import java.util.Date;
import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 宿舍检查记录
 *
 * @author lb
 * @date 2025-01-03 13:33:28
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inspection implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 检查记录编号 */
    @FieldDescription(name = "inspectionId",description = "检查记录编号")
    private Integer inspectionId;

    /** 宿舍编号 */
    @FieldDescription(name = "dormId",description = "宿舍编号")
    private String dormId;

    /** 检查人 */
    @FieldDescription(name = "inspector",description = "检查人")
    private String inspector;

    /** 检查日期 */
    @FieldDescription(name = "inspectionDate",description = "检查日期")
    private Date inspectionDate;

    /** 备注 */
    @FieldDescription(name = "remarks",description = "备注")
    private String remarks;


}
