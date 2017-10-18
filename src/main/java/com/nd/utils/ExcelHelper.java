/**
 *
 */
package com.nd.utils;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nd.domain.Student;

/**
 * 有效数据是从第0个sheet第0行第0列开始的
 */
public class ExcelHelper<T> {

    ExcelExtractor<T> extractor;

    public ExcelHelper(ExcelExtractor<T> extractor) {
        this.extractor = extractor;
    }


    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public List<T> readExcel(String path)
            throws IOException, IntrospectionException, InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (path == null || StringUtils.isBlank(path)) {
            return null;
        }
        else {
            String postfix = getPostfix(path);
            if (StringUtils.isNotBlank(postfix)) {
                if ("xls".equals(postfix)) {
                    return readXls(path);
                }
                else if ("xlsx".equals(postfix)) {
                    return readXlsx(path);
                }
            }
            else {
                System.out.println(path);
            }
        }
        return null;
    }

    public String getPostfix(String path) {
        return path.substring(path.lastIndexOf(".") + 1, path.length());
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<T> readXlsx(String path)
            throws IOException, InvocationTargetException, IntrospectionException, InstantiationException,
            IllegalAccessException {
        System.out.println(path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        T student = null;
        List<T> list = new ArrayList<T>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row 从第0行开始
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    //                    student = new Student();
                    //                    XSSFCell no = xssfRow.getCell(0);
                    //                    XSSFCell name = xssfRow.getCell(1);
                    //                    XSSFCell age = xssfRow.getCell(2);
                    //                    XSSFCell score = xssfRow.getCell(3);
                    //                    student.setNo(getValue(no));
                    //                    student.setName(getValue(name));
                    //                    student.setAge(getValue(age));
                    //                    student.setScore(Float.valueOf(getValue(score)));
                    T domain = this.extractor.extractor(xssfRow);
                    list.add(domain);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param   path of the Excel
     * @return
     * @throws IOException
     */
    public List<T> readXls(String path)
            throws IOException, InvocationTargetException, IntrospectionException, InstantiationException,
            IllegalAccessException {
        System.out.println(path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Student student = null;
        List<T> list = new ArrayList<T>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row从第0行开始
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {// 这个不为空写的好
//                    student = new Student();
//                    HSSFCell no = hssfRow.getCell(0);
//                    HSSFCell name = hssfRow.getCell(1);
//                    HSSFCell age = hssfRow.getCell(2);
//                    HSSFCell score = hssfRow.getCell(3);
//                    student.setNo(getValue(no));
//                    student.setName(getValue(name));
//                    student.setAge(getValue(age));
//                    student.setScore(Float.valueOf(getValue(score)));
//                    list.add(student);
                    list.add((T) this.extractor.extractor(hssfRow));

                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        }
        else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        }
        else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        }
        else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        }
        else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    public static String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }
        else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    public static <T> void writeExcel(String path,List<T> list,Class<T> clazz) {


    }
}