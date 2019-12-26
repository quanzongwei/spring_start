/**
 *
 */
package com.nd.utils;

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

import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 有效数据是从第0个sheet第0行第0列开始的
 */
public class ExcelHelperV2<T> {

    DomainExtractorV2<T> extractor;

    public ExcelHelperV2(DomainExtractorV2<T> extractor) {
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
        List<T> list = new ArrayList<T>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row 从第0行开始
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
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
        List<T> list = new ArrayList<T>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row从第0行开始 此处可修改哦
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {// 这个不为空写的好
                    list.add((T) this.extractor.extractor(hssfRow));
                }
            }
        }
        return list;
    }

    /**
     * 写表依赖于父类,这个待优化
     * created by quanzongwei
     */
    public <T extends BaseDomain> void  writeXls(String path,List<T> list,Class<T> clazz)
            throws IOException, InvocationTargetException, IntrospectionException, InstantiationException,
            IllegalAccessException {
        System.out.println(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("hello");
        for (int rowNum = 0; rowNum < list.size(); rowNum++) {
            HSSFRow row = hssfSheet.createRow(rowNum);
            T domain = list.get(rowNum);
            for (int colNum = 0; colNum <= 2; colNum++) {
                HSSFCell cell = row.createCell(colNum);
                if (colNum == 0) {
                    cell.setCellValue(String.valueOf(domain.get$0()));
                }
                if (colNum == 1) {
                    cell.setCellValue(String.valueOf(domain.get$1()));
                }
                if (colNum == 2) {
                    cell.setCellValue(String.valueOf(domain.get$2()));
                }
            }
        }
        OutputStream out = new FileOutputStream(path);
        hssfWorkbook.write(out);
    }
}