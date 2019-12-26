package com.nd.utils;

import com.nd.model.excel.Category;
import javafx.scene.chart.PieChart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Test;
import org.openxmlformats.schemas.xpackage.x2006.digitalSignature.impl.RelationshipReferenceDocumentImpl;
import org.springframework.scheduling.commonj.ScheduledTimerListener;
import sun.security.action.GetPropertyAction;

import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.*;

/**
 * @author BG388892
 * @date 2019/12/25
 */
public class ExcelTest {




    public static void main(String[] args) throws IOException, InvocationTargetException, IntrospectionException,
            InstantiationException, IllegalAccessException {
        // 其实可以不用加上提取器的
        ExcelHelper<UID> areaExcelHelper = new ExcelHelper<UID>(new DomainExtractor<UID>(UID.class) {
            @Override
            public Map<String, Integer> fieldNameMappingCellIndex() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("uid", 0);
                return map;
            }
        });
        List<UID> areas = areaExcelHelper.readExcel("D:\\桌面\\ulist.xls");
        // System.out.println(areas);
        // System.out.println(areas.size());
        int i = 1;
        Set set = new HashSet();
        for (UID one : areas) {
            // String area = one.getArea();
            String uid = String.valueOf(one.getUid());
            set.add(uid);

        }
        System.out.println(set.size());
        areaExcelHelper.writeXls("D:\\桌面\\ulistCopy.xls", areas, UID.class);
    }


    @Test
    public void testCategorySqlGenerate() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException {
        ExcelHelper<Category> categoryExcelHelper = new ExcelHelper<Category>(new DomainExtractor<Category>(Category.class) {
            @Override
            public Map<String, Integer> fieldNameMappingCellIndex() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("categoryId", 0);
                map.put("storeId", 11);
                map.put("version", 1);
                map.put("creator", 2);
                map.put("updater", 4);
                map.put("categoryName", 7);
                map.put("categoryCode", 8);
                map.put("sortNumber", 9);
                return map;
            }
        });
        List<Category> list =categoryExcelHelper.readExcel("D:\\Data\\category-sync\\uat-category.xls");

        System.out.println("====");
    }


    /**
     * 版本2测试成功
     */
    @Test
    public void testCategorySqlGenerateV2() throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, IOException {
        String str= "categoryId,version,creator,createTime,updater,lastUpdateTime,userName,categoryName,categoryCode,sortNumber,synTime,storeId";
        //size 数据
        String[] split = str.split(",");
        ExcelHelperV2<Category> categoryExcelHelper = new ExcelHelperV2<Category>(new DomainExtractorV2<Category>(Category.class,Arrays.asList(split)){});
        List<Category> list =categoryExcelHelper.readExcel("D:\\Data\\category-sync\\uat-category.xls");

        System.out.println("====");
    }

    @Test
    public void testExcel() throws IOException {
        String path = "D:\\Data\\category-sync\\uat-category.xls";
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
//            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            for (int rowNum = 3; rowNum <= 3; rowNum++) {
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
                    HSSFCell cell0 = hssfRow.getCell(0);
                    HSSFCell cell1 = hssfRow.getCell(1);
                    HSSFCell cell2 = hssfRow.getCell(2);
                    HSSFCell cell3 = hssfRow.getCell(3);
                    HSSFCell cell4 = hssfRow.getCell(4);
                    HSSFCell cell5 = hssfRow.getCell(5);
                    HSSFCell cell6 = hssfRow.getCell(6);
                    HSSFCell cell7 = hssfRow.getCell(7);
                    HSSFCell cell8 = hssfRow.getCell(8);
                    HSSFCell cell9 = hssfRow.getCell(9);
                    HSSFCell cell10 = hssfRow.getCell(10);
                    HSSFCell cell11= hssfRow.getCell(11);
                    CellType cellTypeEnum = cell0.getCellTypeEnum();

                    System.out.println(cell0.getCellTypeEnum().name());
                    System.out.println(cell1.getCellTypeEnum().name());
                    System.out.println(cell2.getCellTypeEnum().name());
                    System.out.println(cell3.getCellTypeEnum().name());
//                    System.out.println(cell4.getCellTypeEnum().name());
//                    System.out.println(cell5.getCellTypeEnum().name());
                    System.out.println("NULL");
                    System.out.println("NULL");
                    System.out.println(cell6.getCellTypeEnum().name());
                    System.out.println(cell7.getCellTypeEnum().name());
                    System.out.println(cell8.getCellTypeEnum().name());
                    System.out.println(cell9.getCellTypeEnum().name());
//                    System.out.println(cell10.getCellTypeEnum().name());
                    System.out.println("NULL");
                    System.out.println(cell11.getCellTypeEnum().name());

                    //日期一定是要进行测试的, 又失去和展示的问题
                    Date dateCellValue = cell3.getDateCellValue();
                    int timezoneOffset = dateCellValue.getTimezoneOffset();
                    System.out.println(timezoneOffset);
                    System.out.println(dateCellValue);
                    System.out.println(cell3.toString());
                }
            }
        }
    }

    @Test
    public void testSystemProperties() {
        // 系统参数
        String language, region, zone,script,country,variant;
        language = AccessController.doPrivileged(
                new GetPropertyAction("user.language", "en"));
        // for compatibility, check for old user.region property
        region = AccessController.doPrivileged(
                new GetPropertyAction("user.region"));
        zone = AccessController.doPrivileged(
                new GetPropertyAction("user.timezone"));
        script = AccessController.doPrivileged(
                new GetPropertyAction("user.script"));
        country = AccessController.doPrivileged(
                new GetPropertyAction("user.country"));
        variant = AccessController.doPrivileged(
                new GetPropertyAction("user.variant"));
        System.out.println(language);// zh
        System.out.println(region);// null
        System.out.println(zone);//Asia/Shanghai
        System.out.println(script);//null
        System.out.println(country);// CN
        System.out.println(variant);//null
    }

    /**
     * 第一步java model定义
     */
    @Test
    public void testTitleSplit() {
        String copyFromExcelTitle = "categoryId\tversion\tcreator\tcreateTime\tupdater\tlastUpdateTime\tuserName\tcategoryName\tcategoryCode\tsortNumber\tsynTime\tstoreId\n";
        // replace all \\t -> ,
        String str= "categoryId,version,creator,createTime,updater,lastUpdateTime,userName,categoryName,categoryCode,sortNumber,synTime,storeId";
        //size 数据
        String[] split = str.split(",");

        for (String one : split) {
            System.out.println("public String {};".replace("{}",one));
        }
    }

}
