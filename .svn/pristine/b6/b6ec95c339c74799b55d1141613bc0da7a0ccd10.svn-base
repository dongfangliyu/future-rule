package com.zw.rule.batchtest.util;

import com.zw.rule.datamanage.po.Field;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class BatchExcelUtil {

    public static <T> void exportExcel(OutputStream out, String exlType, Map<String , Object> mapHeader, Map<String , Object> mapClass, Map<String, List<List<Field>>> map) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Workbook workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }
        for(Map.Entry<String , List<List<Field>>> entry:map.entrySet()){
            Sheet sheet = workbook.createSheet(entry.getKey());
            List<List<Field>> fieldList = entry.getValue();
            String[] headers = (String[]) mapHeader.get(entry.getKey());
            String[] classNames = (String[]) mapClass.get(entry.getKey());

            for(short i = 0; i < headers.length; i++) {
                sheet.setColumnWidth(i, 6400);
            }

            CellStyle cell = workbook.createCellStyle();
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            Font font1 = workbook.createFont();
            font.setFontName("微软雅黑");
            font.setFontHeight((short) 240);
            font.setBoldweight((short)700);
            cell.setFont(font);
            cell.setAlignment((short)2);
            cell.setVerticalAlignment((short)1);
            cell.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cell.setFillPattern((short)1);
            cell.setWrapText(true);
            cell.setBorderBottom((short)1);
            cell.setBorderLeft((short)1);
            cell.setBorderRight((short)1);
            cell.setBorderTop((short)1);
            font1.setFontName("微软雅黑");
            font1.setFontHeight((short)240);
            cellStyle.setFont(font1);
            cellStyle.setAlignment((short)1);
            cellStyle.setVerticalAlignment((short)1);
            cellStyle.setWrapText(true);
            cellStyle.setBorderBottom((short)1);
            cellStyle.setBorderLeft((short)1);
            cellStyle.setBorderRight((short)1);
            cellStyle.setBorderTop((short)1);
            Row row = sheet.createRow(0);
            row.setHeight((short)800);
            for(short i = 0; i < headers.length; i++) {
                Cell index = row.createCell(i);
                index.setCellStyle(cell);
                index.setCellValue(headers[i]+"|"+classNames[i]);
            }
            for(int i = 0;i < fieldList.size();i++){
                List<Field> list = fieldList.get(i);
                row = sheet.createRow(i+1);
                row.setHeight((short)800);
                for(int j = 0; j < list.size(); j++){
                    Field field = list.get(j);
                    Cell cell2 = row.createCell(j);
                    cell2.setCellStyle(cellStyle);
                    cell2.setCellValue(field.getRestrainScope());
                }
            }
        }

        try {
            workbook.write(out);
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static String formatCell(HSSFCell cell) {
        if(cell == null) {
            return "";
        } else {
            switch(cell.getCellType()) {
                case 0:
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    }
                    HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                    return dataFormatter.formatCellValue(cell);
                case 1:
                    return cell.getStringCellValue();
                case 2:
                    return cell.getCellFormula();
                case 3:
                    return "";
                case 4:
                    return String.valueOf(cell.getBooleanCellValue());
                case 5:
                    return String.valueOf(cell.getErrorCellValue());
                default:
                    return "";
            }
        }
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
