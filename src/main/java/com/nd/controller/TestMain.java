package com.nd.controller;

import com.nd.domain.Area;
import com.nd.utils.DomainExtractor;
import com.nd.utils.ExcelHelper;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quanzongwei(207127) on 2017/8/7 0007.
 */
public class TestMain {
    public static void main(String[] args) throws IOException, InvocationTargetException, IntrospectionException,
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
    }
}
