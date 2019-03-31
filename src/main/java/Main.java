public class Main {

    public static void main(String[] args){
       ExcelUtil excelUtil = new ExcelUtil();
       String excelPath = "./source.xlsx";
       String wordPath = "./target/";
       excelUtil.excelToWord(excelPath, wordPath);
    }

}
