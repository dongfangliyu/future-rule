package com.zw.rule.knowleage.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class ExcelUtil {
    public ExcelUtil() {
    }

    public static <T> void exportExcel(OutputStream out, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = ((Workbook)workbook).createSheet("title");

        for(short cellStyle = 0; cellStyle < headers.length; ++cellStyle) {
            if(cellStyle != headers.length - 1 && cellStyle != headers.length - 2) {
                sheet.setColumnWidth(cellStyle, 6400);
            } else {
                sheet.setColumnWidth(cellStyle, 12800);
            }
        }

        CellStyle var31 = ((Workbook)workbook).createCellStyle();
        CellStyle cellStyle1 = ((Workbook)workbook).createCellStyle();
        Font font = ((Workbook)workbook).createFont();
        Font font1 = ((Workbook)workbook).createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 240);
        font.setBoldweight((short)700);
        var31.setFont(font);
        var31.setAlignment((short)2);
        var31.setVerticalAlignment((short)1);
        var31.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        var31.setFillPattern((short)1);
        var31.setWrapText(true);
        var31.setBorderBottom((short)1);
        var31.setBorderLeft((short)1);
        var31.setBorderRight((short)1);
        var31.setBorderTop((short)1);
        font1.setFontName("微软雅黑");
        font1.setFontHeight((short)240);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment((short)1);
        cellStyle1.setVerticalAlignment((short)1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderBottom((short)1);
        cellStyle1.setBorderLeft((short)1);
        cellStyle1.setBorderRight((short)1);
        cellStyle1.setBorderTop((short)1);
        Row row = sheet.createRow(0);
        row.setHeight((short)400);

        for(short it = 0; it < headers.length; ++it) {
            Cell index = row.createCell(it);
            index.setCellStyle(var31);
            index.setCellValue(headers[it]);
        }

        Iterator var32 = list.iterator();
        int var33 = 0;

        while(true) {
            Object e;
            do {
                if(!var32.hasNext()) {
                    try {
                        ((Workbook)workbook).write(out);
                        out.flush();
                        out.close();
                        out = null;
                    } catch (FileNotFoundException var28) {
                        var28.printStackTrace();
                    } catch (IOException var29) {
                        var29.printStackTrace();
                    } finally {
                        try {
                            if(out != null) {
                                out.close();
                            }
                        } catch (IOException var27) {
                            var27.printStackTrace();
                        }

                    }

                    return;
                }

                ++var33;
                row = sheet.createRow(var33);
                row.setHeight((short)800);
                e = var32.next();
            } while(e == null);

            for(int j = 0; j < headers.length; ++j) {
                Cell e1 = row.createCell(j);
                Field field = e.getClass().getDeclaredField(classNames[j]);
                field.setAccessible(true);
                Class valType = field.getType();
                e1.setCellStyle(cellStyle1);
                if("java.lang.String".equalsIgnoreCase(valType.getName())) {
                    e1.setCellValue((String)field.get(e));
                } else if(!"java.lang.Integer".equalsIgnoreCase(valType.getName()) && !"int".equalsIgnoreCase(valType.getName())) {
                    if(!"java.lang.Double".equalsIgnoreCase(valType.getName()) && !"double".equalsIgnoreCase(valType.getName())) {
                        if(!"java.lang.Long".equalsIgnoreCase(valType.getName()) && !"long".equalsIgnoreCase(valType.getName())) {
                            if("java.util.Date".equalsIgnoreCase(valType.getName())) {
                                if(field.get(e) == null) {
                                    e1.setCellValue("");
                                } else {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    e1.setCellValue(sdf.format((Date)field.get(e)));
                                }
                            }
                        } else {
                            e1.setCellValue((double)((Long)field.get(e) == null?0L:((Long)field.get(e)).longValue()));
                        }
                    } else {
                        e1.setCellValue((Double)field.get(e) == null?0.0D:((Double)field.get(e)).doubleValue());
                    }
                } else {
                    if(field.get(e) == null){
                        e1.setCellValue("");
                    }else{
                        e1.setCellValue((double)((Integer)field.get(e)).intValue());
                    }
                }

                field.setAccessible(false);
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

}
