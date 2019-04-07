public class DateUtil {

    /**
     * 中文数字
     */
    private static final String[] CN_NUMBER_NAME = {"O", "一", "二", "三", "四", "五", "六", "七", "八", "九"};


    public static String transform(String date) {
       /* date = "2018年11月13日 ";
        String year = date.substring(0, date.indexOf("年") );
        String month = date.substring(date.indexOf("年") + 1, date.indexOf("月"));
        String day = date.substring(date.indexOf("月") + 1, date.indexOf("日"));*/
//        date = "2018/8/23 ";
        String year = date.substring(0, date.indexOf("/") );
        String month = date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
        String day = date.substring(date.lastIndexOf("/") + 1, date.length());

        StringBuilder cnDate = new StringBuilder();
        for (int i = 0; i < year.length(); i++) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(year.charAt(i)))]);
        }

        cnDate.append("年");

        for (int i = 0; i < month.length(); i++) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(month.charAt(i)))]);
        }

        cnDate.append("月");

        int dayNum = Integer.valueOf(String.valueOf(day.charAt(0)));
        if (day.length() == 1) {
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(day.charAt(0)))]);
        } else {
            if (dayNum == 1) {
                cnDate.append("十");
            } else {
                cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(day.charAt(0)))]);
                cnDate.append("十");
            }
            cnDate.append(CN_NUMBER_NAME[Integer.valueOf(String.valueOf(day.charAt(1)))]);
        }

//        for (int i = 0; i < day.length(); i++) {
//
//        }

        cnDate.append("日");
        return cnDate.toString();

    }
}
