package scaffolding.gui.common.util;

/**
 * @author lb
 */
public class TransferStringUtils {

    // 转换为小驼峰（camelCase）
    public static String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        boolean isNextUpper = false;
        boolean isFirstChar = true;
        for (char ch : str.toCharArray()) {
            if (ch == '_' || ch == '-') {
                isNextUpper = true;
            } else {
                if (isNextUpper) {
                    result.append(Character.toUpperCase(ch));
                    isNextUpper = false;
                } else {
                    if (isFirstChar) {
                        result.append(Character.toLowerCase(ch));
                        isFirstChar = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }


    // 转换为大驼峰（PascalCase）
    public static String toPascalCase(String str) {
        return str.isEmpty() ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static void main(String[] args) {
        String input = "Student_Information_example";

        String camelCase = toCamelCase(input);
        String pascalCase = toPascalCase(input);

        System.out.println("Camel Case: " + camelCase); // studentInformationExample
        System.out.println("Pascal Case: " + pascalCase); // StudentInformationExample
    }
}
