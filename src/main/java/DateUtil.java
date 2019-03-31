public class DateUtil {

    /**
     * 中文数字
     */
    private static final String[] CN_NUMBER_NAME = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};


    public static String transform(String date) {
        date = "2018年11月13日 ";
        String year = date.substring(0, date.indexOf("年") );
        String month = date.substring(date.indexOf("年") + 1, date.indexOf("月"));
        String day = date.substring(date.indexOf("月") + 1, date.indexOf("日"));

        StringBuilder cnDate = new StringBuilder();
        for (int i = 0; i < year.length(); i++) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(year.charAt(i)))]);
        }

        cnDate.append("年");

        for (int i = 0; i < month.length(); i++) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(month.charAt(i)))]);
        }

        cnDate.append("月");

        for (int i = 0; i < day.length(); i++) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(day.charAt(i)))]);
        }

        cnDate.append("日");
        return cnDate.toString();

    }
}
