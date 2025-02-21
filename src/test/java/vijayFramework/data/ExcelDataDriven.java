package vijayFramework.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ExcelDataDriven {

    //Identify test case column from entire excel.
    // Once column is identified then scan entire testcase column to identify the testcases required
    // then fetch the details of that row
    static DataFormatter formatter= new DataFormatter();
    public static ArrayList<String> getData(String testCaseName) throws IOException {
        ArrayList<String> details= new ArrayList<>();
        FileInputStream file= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/excelData.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(file);
        int sheetCount=workbook.getNumberOfSheets();
        for(int i=0;i<sheetCount;i++){
            if(workbook.getSheetName(i).equalsIgnoreCase("ErrorValidationTestdata")){
                XSSFSheet sheet=workbook.getSheetAt(i);
                Iterator<Row> rows=sheet.iterator();
                Row firstRow=rows.next();
                Iterator<Cell> cells=firstRow.cellIterator();
                int k=0;
                int column=0;
                while(cells.hasNext()){
                    Cell cellContent=cells.next();
                    if(cellContent.getStringCellValue().equalsIgnoreCase(testCaseName)){
                        column=k;

                    }
                    k++;
                }

                while(rows.hasNext()){
                    Row r=rows.next();
                    if(r.getCell(column).getStringCellValue().equalsIgnoreCase("IncorrectCreds_Numbers")){
                        Iterator<Cell> eachcell=r.cellIterator();
                        while(eachcell.hasNext()){
                            String value=formatter.formatCellValue(eachcell.next());
                            details.add(value);
                        }
                    }
                }

            }

        }
        return details;
    }

    public static Object[][] getDataFromExcel(String testCaseName) throws IOException {
        ArrayList<String> details= new ArrayList<>();
        Object[][] data = new Object[0][];
        FileInputStream file= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/excelData.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(file);
        int sheetCount=workbook.getNumberOfSheets();
        for(int i=0;i<sheetCount;i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("ErrorValidationTestdata")) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                int rowCount=sheet.getLastRowNum();
                int colCount= sheet.getRow(0).getLastCellNum();
                System.out.println("Total number of rows is "+ rowCount);
                data= new Object[rowCount][colCount-1];
                for(int j=1;j<=rowCount;j++){
                    for(int k=0;k<colCount;k++){
                        if(k!=0){
                            String da=formatter.formatCellValue(sheet.getRow(j).getCell(k));
                            details.add(da);
                            data[j-1][k-1]= da;
                            System.out.println((j-1)+" row  "+ (k-1)+" column    " + data[j-1][k-1]);
                        }
                    }

                }
            }

        }
        System.out.println(Arrays.deepToString(data));
        return data;
    }
    public static ArrayList<String> editDataForSpecificFieldInExcel(String testCaseName) throws IOException {
        ArrayList<String> details= new ArrayList<>();
        Object[][] data = new Object[0][];
        FileInputStream file= new FileInputStream(System.getProperty("user.dir")+"/src/main/java/vijayFramework/resources/FileToUpload.xlsx");
        XSSFWorkbook workbook= new XSSFWorkbook(file);
        int sheetCount=workbook.getNumberOfSheets();
        for(int i=0;i<sheetCount;i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                int rowCount=sheet.getLastRowNum();
                int colCount= sheet.getRow(0).getLastCellNum();
                System.out.println("Total number of rows is "+ rowCount);
                data= new Object[rowCount][colCount-1];
                for(int j=1;j<=rowCount;j++){
                    for(int k=1;k<colCount;k++){
                        String expItemName=formatter.formatCellValue(sheet.getRow(j).getCell(k));
                        if(k==1 && expItemName.equals("Apple")){
                            String da=formatter.formatCellValue(sheet.getRow(j).getCell(k+2));
                            details.add(da);
                            break;
                        }
                        else {

                        }
                    }

                }
            }

        }
        System.out.println(details);
        return details;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> datareceived=editDataForSpecificFieldInExcel("2");
        System.out.println(datareceived);

    }

}
