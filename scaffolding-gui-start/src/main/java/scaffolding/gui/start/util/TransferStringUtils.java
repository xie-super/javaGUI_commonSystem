package scaffolding.gui.start.util;

public class TransferStringUtils {

    // 转换为小驼峰（camelCase）
    public static String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        boolean isNextUpper = false;

        for (char ch : str.toCharArray()) {
            if (ch == '_' || ch == '-') {
                isNextUpper = true; // 下一个字符转换为大写
            } else {
                if (isNextUpper) {
                    result.append(Character.toUpperCase(ch));
                    isNextUpper = false;
                } else {
                    result.append(Character.toLowerCase(ch));
                }
            }
        }
        return result.toString();
    }

    // 转换为大驼峰（PascalCase）
    public static String toPascalCase(String str) {
        String camelCase = toCamelCase(str);
        return camelCase.isEmpty() ? camelCase : Character.toUpperCase(camelCase.charAt(0)) + camelCase.substring(1);
    }

    public static void main(String[] args) {
        String input = "Student_Information_example";

        String camelCase = toCamelCase(input);
        String pascalCase = toPascalCase(input);

        System.out.println("Camel Case: " + camelCase); // studentInformationExample
        System.out.println("Pascal Case: " + pascalCase); // StudentInformationExample
    }
}
