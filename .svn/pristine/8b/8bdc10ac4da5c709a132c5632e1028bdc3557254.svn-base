package com.zw.base.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Created by Administrator on 2017/5/5.
 */
public class ExcelUtil<T> {
    public ExcelUtil() {
    }

    public static <T> void exportFieldExcel(OutputStream out, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = ((Workbook)workbook).createSheet("title");
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 5120);
        sheet.setColumnWidth(2, 5120);
        sheet.setColumnWidth(3, 2560);
        sheet.setColumnWidth(4, 2560);
        sheet.setColumnWidth(5, 19200);
        CellStyle cellStyle = ((Workbook)workbook).createCellStyle();
        CellStyle cellStyle1 = ((Workbook)workbook).createCellStyle();
        Font font = ((Workbook)workbook).createFont();
        Font font1 = ((Workbook)workbook).createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 240);
        font.setBoldweight((short) 700);
        cellStyle.setFont(font);
        cellStyle.setAlignment((short) 2);
        cellStyle.setVerticalAlignment((short) 1);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern((short) 1);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        font1.setFontName("微软雅黑");
        font1.setFontHeight((short) 240);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment((short) 1);
        cellStyle1.setVerticalAlignment((short) 1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderBottom((short) 1);
        cellStyle1.setBorderLeft((short) 1);
        cellStyle1.setBorderRight((short) 1);
        cellStyle1.setBorderTop((short) 1);
        Row row = sheet.createRow(0);
        row.setHeight((short) 400);

        for(short it = 0; it < headers.length; ++it) {
            Cell index = row.createCell(it);
            index.setCellStyle(cellStyle);
            index.setCellValue(headers[it]);
        }

        Iterator var31 = list.iterator();
        int var32 = 0;

        while(true) {
            Object e;
            do {
                if(!var31.hasNext()) {
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

                ++var32;
                row = sheet.createRow(var32);
                row.setHeight((short) 400);
                e = var31.next();
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
                    e1.setCellValue((double)((Integer)field.get(e)).intValue());
                }

                field.setAccessible(false);
            }
        }
    }

    public static <T> void exportCustListExcel(OutputStream out, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = ((Workbook)workbook).createSheet("title");
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 2560);
        sheet.setColumnWidth(3, 2560);
        sheet.setColumnWidth(4, 2560);
        sheet.setColumnWidth(5, 2560);
        sheet.setColumnWidth(6, 2560);
        sheet.setColumnWidth(7, 2560);
        sheet.setColumnWidth(8, 2560);
        sheet.setColumnWidth(9, 2560);
        sheet.setColumnWidth(10, 2560);
        sheet.setColumnWidth(11, 2560);
        sheet.setColumnWidth(12, 2560);
        sheet.setColumnWidth(13, 2560);
        sheet.setColumnWidth(14, 2560);
        sheet.setColumnWidth(15, 2560);
        sheet.setColumnWidth(16, 2560);
        sheet.setColumnWidth(17, 2560);
        sheet.setColumnWidth(18, 2560);
        sheet.setColumnWidth(19, 2560);
        CellStyle cellStyle = ((Workbook)workbook).createCellStyle();
        CellStyle cellStyle1 = ((Workbook)workbook).createCellStyle();
        Font font = ((Workbook)workbook).createFont();
        Font font1 = ((Workbook)workbook).createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 240);
        font.setBoldweight((short) 700);
        cellStyle.setFont(font);
        cellStyle.setAlignment((short) 2);
        cellStyle.setVerticalAlignment((short) 1);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern((short) 1);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        font1.setFontName("微软雅黑");
        font1.setFontHeight((short) 240);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment((short) 1);
        cellStyle1.setVerticalAlignment((short) 1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderBottom((short) 1);
        cellStyle1.setBorderLeft((short) 1);
        cellStyle1.setBorderRight((short) 1);
        cellStyle1.setBorderTop((short) 1);
        Row row = sheet.createRow(0);
        row.setHeight((short) 400);

        for(short it = 0; it < headers.length; ++it) {
            Cell index = row.createCell(it);
            index.setCellStyle(cellStyle);
            index.setCellValue(headers[it]);
        }

        Iterator var31 = list.iterator();
        int var32 = 0;

        while(true) {
            Object e;
            do {
                if(!var31.hasNext()) {
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

                ++var32;
                row = sheet.createRow(var32);
                row.setHeight((short) 400);
                e = var31.next();
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
                    e1.setCellValue((double)((Integer)field.get(e)).intValue());
                }

                field.setAccessible(false);
            }
        }
    }

    public static <T> void createCustListExcel(String path, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = ((Workbook)workbook).createSheet("title");
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 2560);
        sheet.setColumnWidth(3, 2560);
        sheet.setColumnWidth(4, 2560);
        sheet.setColumnWidth(5, 2560);
        sheet.setColumnWidth(6, 2560);
        sheet.setColumnWidth(7, 2560);
        sheet.setColumnWidth(8, 2560);
        sheet.setColumnWidth(9, 2560);
        sheet.setColumnWidth(10, 2560);
        sheet.setColumnWidth(11, 2560);
        sheet.setColumnWidth(12, 2560);
        sheet.setColumnWidth(13, 2560);
        sheet.setColumnWidth(14, 2560);
        sheet.setColumnWidth(15, 2560);
        sheet.setColumnWidth(16, 2560);
        sheet.setColumnWidth(17, 2560);
        sheet.setColumnWidth(18, 2560);
        sheet.setColumnWidth(19, 2560);
        CellStyle cellStyle = ((Workbook)workbook).createCellStyle();
        CellStyle cellStyle1 = ((Workbook)workbook).createCellStyle();
        Font font = ((Workbook)workbook).createFont();
        Font font1 = ((Workbook)workbook).createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 240);
        font.setBoldweight((short) 700);
        cellStyle.setFont(font);
        cellStyle.setAlignment((short) 2);
        cellStyle.setVerticalAlignment((short) 1);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern((short) 1);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        font1.setFontName("微软雅黑");
        font1.setFontHeight((short) 240);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment((short) 1);
        cellStyle1.setVerticalAlignment((short) 1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderBottom((short) 1);
        cellStyle1.setBorderLeft((short) 1);
        cellStyle1.setBorderRight((short) 1);
        cellStyle1.setBorderTop((short) 1);
        Row row = sheet.createRow(0);
        row.setHeight((short) 400);

        for(short it = 0; it < headers.length; ++it) {
            Cell index = row.createCell(it);
            index.setCellStyle(cellStyle);
            index.setCellValue(headers[it]);
        }

        Iterator var22 = list.iterator();
        int var23 = 0;

        while(true) {
            Object e;
            do {
                if(!var22.hasNext()) {
                    try {
                        FileOutputStream var24 = new FileOutputStream(path);
                        ((Workbook)workbook).write(var24);
                        var24.close();
                        e = null;
                    } catch (FileNotFoundException var20) {
                        var20.printStackTrace();
                    } catch (IOException var21) {
                        var21.printStackTrace();
                    }

                    return;
                }

                ++var23;
                row = sheet.createRow(var23);
                row.setHeight((short) 400);
                e = var22.next();
            } while(e == null);

            for(int j = 0; j < headers.length; ++j) {
                Cell cell = row.createCell(j);
                Field field = e.getClass().getDeclaredField(classNames[j]);
                field.setAccessible(true);
                Class valType = field.getType();
                cell.setCellStyle(cellStyle1);
                if("java.lang.String".equalsIgnoreCase(valType.getName())) {
                    cell.setCellValue((String)field.get(e));
                } else if(!"java.lang.Integer".equalsIgnoreCase(valType.getName()) && !"int".equalsIgnoreCase(valType.getName())) {
                    if(!"java.lang.Double".equalsIgnoreCase(valType.getName()) && !"double".equalsIgnoreCase(valType.getName())) {
                        if(!"java.lang.Long".equalsIgnoreCase(valType.getName()) && !"long".equalsIgnoreCase(valType.getName())) {
                            if("java.util.Date".equalsIgnoreCase(valType.getName())) {
                                if(field.get(e) == null) {
                                    cell.setCellValue("");
                                } else {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    cell.setCellValue(sdf.format((Date)field.get(e)));
                                }
                            }
                        } else {
                            cell.setCellValue((double)((Long)field.get(e) == null?0L:((Long)field.get(e)).longValue()));
                        }
                    } else {
                        cell.setCellValue((Double)field.get(e) == null?0.0D:((Double)field.get(e)).doubleValue());
                    }
                } else {
                    cell.setCellValue((double)((Integer)field.get(e)).intValue());
                }

                field.setAccessible(false);
            }
        }
    }

    public static <T> void exportEngineTestResultExcel(OutputStream out, String exlType, String[] headers, String[] classNames, List<T> list) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object workbook;
        if(exlType.equalsIgnoreCase("xlsx")) {
            workbook = new SXSSFWorkbook(200);
        } else {
            workbook = new HSSFWorkbook();
        }

        Sheet sheet = ((Workbook)workbook).createSheet("title");
        sheet.setColumnWidth(0, 2560);
        sheet.setColumnWidth(1, 3840);
        sheet.setColumnWidth(2, 3840);
        sheet.setColumnWidth(3, 3840);
        sheet.setColumnWidth(4, 3840);
        sheet.setColumnWidth(5, 3840);
        sheet.setColumnWidth(6, 5120);
        sheet.setColumnWidth(7, 5120);
        CellStyle cellStyle = ((Workbook)workbook).createCellStyle();
        CellStyle cellStyle1 = ((Workbook)workbook).createCellStyle();
        Font font = ((Workbook)workbook).createFont();
        Font font1 = ((Workbook)workbook).createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 240);
        font.setBoldweight((short) 700);
        cellStyle.setFont(font);
        cellStyle.setAlignment((short) 2);
        cellStyle.setVerticalAlignment((short) 1);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern((short) 1);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderTop((short) 1);
        font1.setFontName("微软雅黑");
        font1.setFontHeight((short) 240);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment((short) 1);
        cellStyle1.setVerticalAlignment((short) 1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderBottom((short) 1);
        cellStyle1.setBorderLeft((short) 1);
        cellStyle1.setBorderRight((short) 1);
        cellStyle1.setBorderTop((short) 1);
        Row row = sheet.createRow(0);
        row.setHeight((short) 400);

        for(short it = 0; it < headers.length; ++it) {
            Cell index = row.createCell(it);
            index.setCellStyle(cellStyle);
            index.setCellValue(headers[it]);
        }

        Iterator var31 = list.iterator();
        int var32 = 0;

        while(true) {
            Object e;
            do {
                if(!var31.hasNext()) {
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

                ++var32;
                row = sheet.createRow(var32);
                row.setHeight((short) 400);
                e = var31.next();
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
                    e1.setCellValue((double)((Integer)field.get(e)).intValue());
                }

                field.setAccessible(false);
            }
        }
    }

    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                Row fRow = sheet.getRow(firstRow);
                Cell fCell = fRow.getCell(firstColumn);
                return getCellValue(fCell);
            }
        }

        return null;
    }

    public static boolean isMergedRow(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow && column >= firstColumn && column <= lastColumn) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMergedCol(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(column == firstColumn && column == lastColumn && row >= firstRow && row <= lastRow) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return true;
            }
        }

        return false;
    }

    public static String getCellValue(Cell cell) {
        HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
        return cell == null?"":(cell.getCellType() == 1?cell.getStringCellValue():(cell.getCellType() == 4?String.valueOf(cell.getBooleanCellValue()):(cell.getCellType() == 2?cell.getCellFormula():(cell.getCellType() == 0?dataFormatter.formatCellValue(cell):""))));
    }
}

