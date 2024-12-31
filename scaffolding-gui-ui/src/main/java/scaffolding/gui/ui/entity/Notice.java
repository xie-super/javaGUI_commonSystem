package scaffolding.gui.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
    String title;
    String content;
    Date createTime;
    String creator;
    String type;
    String modifier;
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
