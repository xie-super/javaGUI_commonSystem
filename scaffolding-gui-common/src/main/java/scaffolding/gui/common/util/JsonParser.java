package scaffolding.gui.common.util;

import com.alibaba.fastjson.JSON;
import scaffolding.gui.start.init.UserConfig;

import java.io.*;

/**
 * @author lb
 */
public class JsonParser {


    public static UserConfig parseUserConfig() throws Exception {
        InputStream inputStream = JsonParser.class.getClassLoader().getResourceAsStream("settings.json");

        if (inputStream == null) {
            System.out.println("File not found in resources!");
            return null;
        }
        // 读取文件内容为字符串
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();

        UserConfig userConfig = JSON.parseObject(jsonContent.toString(), UserConfig.class);

        return userConfig;
    }

    public static void main(String[] args) throws Exception {
        parseUserConfig();
    }
}
