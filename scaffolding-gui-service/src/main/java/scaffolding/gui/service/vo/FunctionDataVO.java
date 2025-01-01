package scaffolding.gui.service.vo;

import lombok.Data;
import scaffolding.gui.start.config.UserConfig.User.Function;

import java.util.List;
import java.util.Map;

/**
 * @author superxie
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
