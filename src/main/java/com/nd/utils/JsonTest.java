package com.nd.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * Created by quanzongwei(207127) on 2017/8/9 0009.
 */
public class JsonTest {

    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream("D:\\txt.txt");
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader reader1 = new BufferedReader(reader);
        String str = reader1.readLine();

        JsonNode node = JsonUtil.getMapper().readTree(str);
        ArrayNode arrayNode = (ArrayNode) node;
        Iterator<JsonNode> one = arrayNode.elements();
        int i = 1;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row rowHead = sheet.createRow(0);
        rowHead.createCell(0).setCellValue("日期时间");
        rowHead.createCell(1).setCellValue("内容(json字符串)");
        while (one.hasNext()) {
            JsonNode jnode = one.next();
            String date = jnode.path("_source").path("@_timestamp").asText();
            ///
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(date);
            row.createCell(1).setCellValue(new HSSFRichTextString(jnode.toString()));
            //
            System.out.println("## no:" + i++);
            System.out.println("日期:" + date + ":" + jnode.toString());
        }
        OutputStream outputStream = new FileOutputStream("D:\\pk2.xls");
        wb.write(outputStream);
    }
}
