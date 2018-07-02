package com.nd.utils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by quanzongwei(207127) on 2017/8/7 0007.
 */
public class TestMain {
   /* public static void main(String[] args) throws IOException, InvocationTargetException, IntrospectionException,
            InstantiationException, IllegalAccessException {
        //其实可以不用加上提取器的
        ExcelHelper<Area> areaExcelHelper = new ExcelHelper<Area>(new DomainExtractor<Area>(Area.class) {
            @Override
            public Map<String, Integer> fieldNameMappingCellIndex() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("province", 0);
                map.put("city", 1);
                map.put("area", 2);
                map.put("areaId", 3);
                return map;
            }
        });
        List<Area> areas = areaExcelHelper.readExcel("D:\\testData.xls");
        System.out.println(areas);
        System.out.println(areas.size());
        int i = 1;
        for (Area one : areas) {
//            String area = one.getArea();
            String area = one.getCity();
            Integer areaId = one.getAreaId();
            StringBuilder builder = new StringBuilder("");
            areaId = areaId / 100 * 100;
            builder.append("update `area` set `area_name`='" + area + "' where `id`=" + areaId + ";");
            System.out.println("### no: "+i+++" ");
            System.out.println(builder.toString());
        }
    }*/

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
}
