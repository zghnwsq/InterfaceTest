package com.TestFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
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
            int maxColumnCount = sheet.getRow(0).getPhysicalNumberOfCells();
            for (Row row : sheet) {
                ArrayList<String> list = new ArrayList<String>();
//                System.out.println(row.getPhysicalNumberOfCells()); //debug
//                System.out.println(row.getCell(0));
                for(int i = 0;i<maxColumnCount;i++){
                		Cell cell = row.getCell(i);
//                		System.out.println(cell);
                		if(cell ==null){
                			list.add("");
                		}else{
                			cell.setCellType(CellType.STRING);
                			list.add(cell.getStringCellValue());
                		}
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
    
    public static Map<String, List<List<String>>> readExcelSheets(String path) {
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        // return a list contains many list

        //List<List<List<String>>> sheets = new ArrayList<List<List<String>>>();
        Map<String, List<List<String>>> sheets = new HashMap<String, List<List<String>>>();
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

            //读取工作页sheet
            int sheetCount = wb.getNumberOfSheets();
            //Sheet sheet = wb.getSheetAt(0);
            //Sheet sheet = wb.getSheet(sheetName);
            for(int i=0;i<sheetCount;i++) {
                List<List<String>> lists = new ArrayList<List<String>>();
            		Sheet sheet = wb.getSheetAt(i);
            		int maxColumnCount = sheet.getRow(0).getPhysicalNumberOfCells();
	            //第一行为标题
	            for (Row row : sheet) {
	                ArrayList<String> list = new ArrayList<String>();
	                for(int j = 0; j<maxColumnCount;j++){
	            			Cell cell = row.getCell(j);
	                		if(cell ==null){
	                			list.add("");
	                		}else{
	                			cell.setCellType(CellType.STRING);
	                			list.add(cell.getStringCellValue());
	                		}
	            		}
//	                for (Cell cell : row) {
//	                    //根据不同类型转化成字符串
//	                    //cell.setCellType(Cell.CELL_TYPE_STRING);
//	                    cell.setCellType(CellType.STRING);
//	                    list.add(cell.getStringCellValue());
//	                }
	                lists.add(list); //添加行
	            }
	            sheets.put(String.valueOf(i), lists);  //添加sheet
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
        return sheets;
    }
    
    //写入excel 
    public static void writeExcel(List<String[]> msg, String path, String sheetName) throws IOException{ 
    	if(msg.isEmpty()) {
    		return;
    	}
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        InputStream is = null;
        Workbook wb = null;
        is = new FileInputStream(path);
        //获取工作薄
        //Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(is);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet;
    	if(sheetName.equals("")) {
    		sheet = wb.getSheetAt(0);
    	}else {
    		sheet = wb.getSheet(sheetName);
    	}
        for(String[] row : msg) {
//        	int i = msg.indexOf(row)+1;
        	int i = Integer.valueOf(row[0]);
        	Row r = sheet.getRow(i);
        	Cell cell6 = r.createCell(Integer.valueOf(row[1]));
        	cell6.setCellType(CellType.STRING);
        	cell6.setCellValue(row[2]);
        	if(!row[3].equals("")) {
	        	Cell cell7 = r.createCell(Integer.valueOf(row[1])+1);
	        	cell7.setCellType(CellType.STRING);
	        	cell7.setCellValue(row[3]);       
        	}
        }
        File file = new File(path.replace(".x", "_result.x"));//Excel文件生成后存储的位置。        
        OutputStream fos  = null;  
        try  
        {  
            fos = new FileOutputStream(file);  
            wb.write(fos);  
            //os.close();  
            fos.close();
            wb.close();
        }catch (Exception e){  
            e.printStackTrace();  
        }             
    }
    
    //写入excel
    public static void writeExcel(List<String[]> msg, String path, int sheetIndex) throws IOException{ 
    	if(msg.isEmpty()) {
    		return;
    	}
        String fileType = path.substring(path.lastIndexOf(".") + 1);
        InputStream is = null;
        Workbook wb = null;
        File file = new File(path.replace(".x", "_result.x"));
        if(file.exists()) {
        	is = new FileInputStream(path.replace(".x", "_result.x"));
        }else {
        	is = new FileInputStream(path);
        }
        //获取工作薄
        //Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(is);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet;
//    	if(sheetName.equals("")) {
//    		sheet = wb.getSheetAt(0);
//    	}else {
//    		sheet = wb.getSheet(sheetName);
//    	}
        sheet = wb.getSheetAt(sheetIndex);
        //msg: 0行序号, 1列序号, 2结果, 3详细结果
        for(String[] row : msg) {
//        	int i = msg.indexOf(row)+1;
        	int i = Integer.valueOf(row[0]); //要写入的行序号
        	Row r = sheet.getRow(i);
        	Cell cell6 = r.createCell(Integer.valueOf(row[1]));
        	cell6.setCellType(CellType.STRING);
        	cell6.setCellValue(row[2]);
        	if(!row[3].equals("")) {
	        	Cell cell7 = r.createCell(Integer.valueOf(row[1])+1);
	        	cell7.setCellType(CellType.STRING);
	        	cell7.setCellValue(row[3]);       
        	}
        }
         
        //File file = new File(path.replace(".x", "_result.x"));//Excel文件生成后存储的位置。
        OutputStream fos  = null;  
        try  
        {  
            fos = new FileOutputStream(file);  
            wb.write(fos);  
            //os.close();  
            fos.close();
            wb.close();
        }catch (Exception e){  
            e.printStackTrace();  
        }             
    }


}
