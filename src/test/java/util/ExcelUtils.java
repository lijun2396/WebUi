package util;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Excel导入导出工具类
 */

public class ExcelUtils {
    public static Map<String,String[]> xlsGetLoginInfo(){
        FileInputStream fileInput=null;
        try {
            Map<String,String[]> map=new HashMap<>();
            fileInput=new FileInputStream(PropertiesReader.readProperties().get("login"));
            Workbook workbook= WorkbookFactory.create(fileInput);
            int numberOfSheets=workbook.getNumberOfSheets();
            String[] sheetName=new String[numberOfSheets];
            for (int x=0;x<numberOfSheets;x++){
                sheetName[x]=workbook.getSheetName(x);
            }
            for (String name:sheetName){
                String[] str=new String[2];
                Sheet sheet=workbook.getSheet(name);
                str[0]=sheet.getRow(1).getCell(0).getStringCellValue();
                str[1]=sheet.getRow(1).getCell(1).getStringCellValue();
                map.put(name,str);
            }
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
