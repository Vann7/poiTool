import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    private String excelPath;
    private String wordPath;
    private String tempPath;
   private Map<Integer, ArrayList<String>> resultData = new HashMap<>();
    private File wordFile;

    public  void excelToWord(String excelPath, String wordPath, String tempPath) {
       this.excelPath = excelPath;
       this.wordPath = wordPath;
        this.tempPath = tempPath;

        File file = new File(excelPath);
        if (!file.exists()) {
            return;
        }
        try {
            InputStream is = new FileInputStream(file.getAbsoluteFile());
            @SuppressWarnings("resource")
            XSSFWorkbook wb = new XSSFWorkbook(is);

            XSSFSheet sheet = wb.getSheetAt(0); // 获取第一个sheet表

            int start = sheet.getFirstRowNum();
            int end = sheet.getLastRowNum();
            for (int runNum = start + 1; runNum <= end; runNum++) {
                Row row = sheet.getRow(runNum); // Get the specified line
                //row1.getRowNum()
                ArrayList<String> list = new ArrayList<>();
//                StringBuilder sb = new StringBuilder();
                // Traversing the row ,get the specifity column
                int start_cell = row.getFirstCellNum();
                int end_cell = row.getLastCellNum();
                for(int i=start_cell; i<end_cell; i++) {
                    Cell cell = row.getCell(i); // 获取指定列
                    if (cell == null) continue;
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (i == end_cell - 1 ) {
                        if (i == 6) {
                            String date = cell.getStringCellValue();
                            LocalDate localDate = LocalDate.ofEpochDay(Long.valueOf(date));
                            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
                            String text = localDate.minusYears(70).format(formatters);
                            list.add(text);
                        }
                    } else {
                        list.add(cell.getStringCellValue());
                    }
                }
                resultData.put(runNum, list);
            }

            createWord();
            writeToWord();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToWord() {

    }


    public void createWord() {
        File file = new File(wordPath);

        if (!file.exists()) file.mkdirs();
        int size = resultData.size();
        for(int i=2; i <= size; i++) {
                String fileName = resultData.get(i).get(3) + resultData.get(i).get(5) + resultData.get(i).get(2) + ".doc";
                OutputStream os = null;
                try {
                    boolean flag = copyFile(tempPath, wordPath + fileName);
                    if (!flag) return;
                    InputStream fis = new FileInputStream(wordFile.getPath());
                    HWPFDocument document = new HWPFDocument(fis);

                    Range range = document.getRange();

                    //中标通知书
                    getModel(range, i);

                    //中标服务费通知单
//                    getModel2(range, i);

                    os = new FileOutputStream(wordFile.getPath());
                    document.write(os);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }

    }

    // 文件复制
    public boolean copyFile(String source, String copy) throws Exception {

        File source_file = new File(source);
        wordFile = new File(copy);

        if (!source_file.exists()) {
            throw new IOException("文件复制失败：源文件（" + source_file + "） 不存在");
        }
        if (wordFile.isDirectory()) {
            throw new IOException("文件复制失败：复制路径（" + wordFile + "） 错误");
        }
        File parent = wordFile.getParentFile();
        // 创建复制路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        // 创建复制文件
        if (!wordFile.exists()) {
            wordFile.createNewFile();
        }

        FileInputStream fis = new FileInputStream(source_file);
        FileOutputStream fos = new FileOutputStream(wordFile);

        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] KB = new byte[1024];
        int index;
        while ((index = bis.read(KB)) != -1) {
            bos.write(KB, 0, index);
        }

        bos.close();
        bis.close();
        fos.close();
        fis.close();

        if (!wordFile.exists()) {
            return false;
        } else if (source_file.length() != wordFile.length()) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * 中标通知书
     * @param range
     * @param i
     */
    public void getModel(Range range, int i) {

        range.replaceText("${user}", resultData.get(i).get(3));//中标人
        range.replaceText("${project}", resultData.get(i).get(4)); //项目名称
        range.replaceText("${code}", resultData.get(i).get(5)); //招标编号
        range.replaceText("${baojian}", resultData.get(i).get(2)); //包件号
        range.replaceText("${content}", resultData.get(i).get(7)); //中标内容
        range.replaceText("${price}", resultData.get(i).get(8)); //中标金额


        String date;
        String type = resultData.get(i).get(0);

        if (type.equals("1")) {
            date = DateUtil.transform("2018/8/9"); //中标通知书日期
        } else {
            date = DateUtil.transform("2018/8/23"); //中标通知书日期
        }
        range.replaceText("${date}", date);
    }


    /**
     * 中标服务通知单
     * @param range
     * @param i
     */
    public void getModel2(Range range, int i) {
        String user = resultData.get(i).get(3);
        String project = resultData.get(i).get(4);
        String code = resultData.get(i).get(5);
        String baojian = resultData.get(i).get(2);
        String price = resultData.get(i).get(7);
        String serverPrice = resultData.get(i).get(8);

        range.replaceText("${user}", user);//中标人
        range.replaceText("${project}", project); //项目名称
        range.replaceText("${code}", code); //招标编号
        range.replaceText("${baojian}", baojian); //包件号
        range.replaceText("${price}", price); //中标金额
        range.replaceText("${serverPrice}", serverPrice); //中标服费

        String date;
        String type = resultData.get(i).get(0);

        if (type.equals("1")) {
            date = DateUtil.transform("2018/8/9"); //中标通知书日期
        } else {
            date = DateUtil.transform("2018/8/23"); //中标通知书日期
        }
        range.replaceText("${date}", date);
//        String date = DateUtil.transform(resultData.get(i).get(9)); //中标通知书日期
//        range.replaceText("${date}", date);
    }





}
