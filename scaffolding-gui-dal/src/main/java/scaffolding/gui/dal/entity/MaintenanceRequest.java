package scaffolding.gui.dal.entity;

import java.util.Date;
import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 维修请求记录
 *
 * @author lb
 * @date 2025-01-03 12:02:22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 维修请求编号 */
    @FieldDescription(name = "requestId",description = "维修请求编号")
    private Integer requestId;

    /** 宿舍编号 */
    @FieldDescription(name = "dormId",description = "宿舍编号")
    private String dormId;

    /** 问题描述 */
    @FieldDescription(name = "issueDescription",description = "问题描述")
    private String issueDescription;

    /** 请求日期 */
    @FieldDescription(name = "requestDate",description = "请求日期")
    private Date requestDate;

    /** 状态 */
    @FieldDescription(name = "status",description = "状态")
    private String status;

    /** 解决日期 */
    @FieldDescription(name = "resolvedDate",description = "解决日期")
    private Date resolvedDate;


}
