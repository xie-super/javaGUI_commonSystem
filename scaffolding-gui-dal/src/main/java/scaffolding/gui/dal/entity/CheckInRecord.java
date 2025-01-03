package scaffolding.gui.dal.entity;

import java.util.Date;
import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 入住记录
 *
 * @author lb
 * @date 2025-01-03 12:02:22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckInRecord implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 入住记录编号 */
    @FieldDescription(name = "recordId",description = "入住记录编号")
    private Integer recordId;

    /** 学生编号 */
    @FieldDescription(name = "studentId",description = "学生编号")
    private String studentId;

    /** 宿舍编号 */
    @FieldDescription(name = "dormId",description = "宿舍编号")
    private String dormId;

    /** 入住日期 */
    @FieldDescription(name = "checkInDate",description = "入住日期")
    private Date checkInDate;

    /** 退宿日期 */
    @FieldDescription(name = "checkOutDate",description = "退宿日期")
    private Date checkOutDate;


}
