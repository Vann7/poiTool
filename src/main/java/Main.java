public class Main {

    public static void main(String[] args){
       ExcelUtil excelUtil = new ExcelUtil();
       String excelPath = "/Users/cec/Desktop/file/中标通知书.xlsx";
       String wordPath = "/Users/cec/Desktop/file/中标通知书/";
       String tempPath = "/Users/cec/Desktop/file/中标通知书.doc";

      /*  String excelPath = "/Users/cec/Desktop/file/中标服务费通知单.xlsx";
        String wordPath = "/Users/cec/Desktop/file/中标服务费通知单/";
        String tempPath = "/Users/cec/Desktop/file/中标服务费通知单.doc";*/

       excelUtil.excelToWord(excelPath, wordPath, tempPath);
    }

}
