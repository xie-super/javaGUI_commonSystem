package scaffolding.gui.common.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lb
 */
@Data
public class FunctionDataVO {
    private String tableName;
    private String functionName;
    private String panelName;
    private Map<String, String> functionMapping;
    private List<String> functionHeaderList;
    private List<List<Object>> dataList;


}
