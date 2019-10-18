package com.bstek.urule.springboot.utils;

public class JSONFormat {
	/**
	 * 打印输入到控制台
	 * @param jsonStr
	 * @author   lizhgb
	 * @Date   2015-10-14 下午1:17:22
	 */
	public static String printJson(String jsonStr){
		String formatJson = formatJsonhtml(jsonStr);
		return formatJson;
	}
   
	/**
	 * 格式化
	 * @param jsonStr
	 * @return
	 * @author   lizhgb
	 * @Date   2015-10-14 下午1:17:35
	 */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     * @param sb
     * @param indent
     * @author   lizhgb
     * @Date   2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
    
    
	/**
	 * 格式化
	 * @param jsonStr
	 * @return
	 * @author   lizhgb
	 * @Date   2015-10-14 下午1:17:35
	 */
    public static String formatJsonhtml(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean stringFlag = false;//字符串标志
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    if (stringFlag) {
                        sb.append(current);
                        break;
                    }
                    sb.append(current);
                    sb.append("<br/>");
                    indent++;
                    addIndentBlankhtml(sb, indent);
                    break;
                case '}':
                case ']':
                    if (stringFlag){
                        sb.append(current);
                        break;
                    }
                    sb.append("<br/>");
                    indent--;
                    addIndentBlankhtml(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append("<br/>");
                        addIndentBlankhtml(sb, indent);
                    }
                    break;
                case '\"':
                    if (stringFlag){
                        stringFlag = false;
                        sb.append(current);
                        break;
                    }else{
                        stringFlag = true;
                        sb.append(current);
                        break;
                    }
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     * @param sb
     * @param indent
     * @author   lizhgb
     * @Date   2015-10-14 上午10:38:04
     */
    private static void addIndentBlankhtml(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }
}
