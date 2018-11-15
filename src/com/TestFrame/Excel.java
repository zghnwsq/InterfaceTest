package com.TestFrame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;


public class Excel {
	
    public static List<List<String>> readExcel(String path, String sheetName) {
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        // return a list contains many list
        List<List<String>> lists = new ArrayList<List<String>>();
        //读取excel文件
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(path);
            //获取工作薄
            //Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }

            //读取第一个工作页sheet
            //Sheet sheet = wb.getSheetAt(0);
            Sheet sheet = wb.getSheet(sheetName);
            //第一行为标题
            for (Row row : sheet) {
                ArrayList<String> list = new ArrayList<String>();
                for (Cell cell : row) {
                    //根据不同类型转化成字符串
                    //cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellType(CellType.STRING);
                    list.add(cell.getStringCellValue());
                }
                lists.add(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            		wb.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }
    
    //写入excel
    public static void writeExcel(ArrayList<ArrayList<String>> result,String path){  
        if(result == null){  
            return;  
        }  
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("sheet1");  
        for(int i = 0 ;i < result.get(0).size() ; i++){  
            HSSFRow row = sheet.createRow(i);  
                for(int j = 0; j < result.size() ; j ++){
                    HSSFCell cell = row.createCell((short)j);
                    cell.setCellValue(result.get(j).get(i).toString());  
                }  
        }  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        try  
        {  
            wb.write(os);  
        } catch (IOException e){  
            e.printStackTrace();  
        }  
        byte[] content = os.toByteArray();  
        File file = new File(path);//Excel文件生成后存储的位置。  
        OutputStream fos  = null;  
        try  
        {  
            fos = new FileOutputStream(file);  
            wb.write(fos);  
            os.close();  
            fos.close();
            wb.close();
        }catch (Exception e){  
            e.printStackTrace();  
        }             
    } 


}
