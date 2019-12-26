package com.nd.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.aspectj.weaver.reflect.ReflectionWorld;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by quanzongwei(207127) on 2017/8/7 0007.
 */
@Getter
@Setter
public abstract class DomainExtractorV2<T>{
    Class<T> tClass;

    /**
     * 字段名和索引的map关系
     * key: 字段名
     * value: 索引
     */
    Map<String, Integer> fieldIndexMap = new HashMap<String, Integer>();

    List fieldList = new ArrayList();

    public DomainExtractorV2(Class<T> tClass, List<String> fieldList) {
        this.tClass = tClass;
        this.fieldList = fieldList;
        this.generateMapping(fieldList);
    }

    private void generateMapping(List<String> fieldList) {
        for (int i = 0; i < fieldList.size(); i++) {
            fieldIndexMap.put(fieldList.get(i), i);
        }
    }

    public T extractor(Row row) throws IllegalAccessException, InstantiationException, IntrospectionException,
            InvocationTargetException {

        T instance = tClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object value = getValueFromCellInRow(propertyDescriptor, row);

            Method writeMethod = propertyDescriptor.getWriteMethod();

            writeMethod.invoke(instance, value);
        }
        return instance;
    }

    /**
     * 支持的返回类型, 目前足够了
     * Long
     * Integer
     * Double
     * String
     */
    private Object getValueFromCellInRow(PropertyDescriptor pd, Row row) {
        //行号, 用于打印错误日志
        int rowNum = row.getRowNum();
        //todo qzw test name and descriptionName
        String name = pd.getName();
        Integer columnIndex = fieldIndexMap.get(name);
        Cell cell = row.getCell(columnIndex);
        //cell 可能为空, 一般情况不期望为空
        if (cell == null) {
            System.out.println("ERROR, cell 数据不存在,row:{},column:{} "
                    .replaceFirst("\\{}", String.valueOf(rowNum))
                    .replaceFirst("\\{}", String.valueOf(columnIndex))
            );
            return null;
        }
        //数据类型处理
        Class<?> propertyType = pd.getPropertyType();
        CellType cellTypeEnum = cell.getCellTypeEnum();
        //Integer
        if (propertyType==Integer.class||propertyType==int.class) {
            if (!cellTypeEnum.equals(CellType.NUMERIC)) {
                throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际类型:{}; row:{},column:{}"
                        .replaceFirst("\\{}", "Integer")
                        .replaceFirst("\\{}", cellTypeEnum.name())
                        .replaceFirst("\\{}", String.valueOf(rowNum))
                        .replaceFirst("\\{}", String.valueOf(columnIndex)));
            }
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        }

        //Long
        if (propertyType==Long.class||propertyType==Long.class) {
            if (!cellTypeEnum.equals(CellType.NUMERIC)) {
                throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际类型:{}; row:{},column:{}"
                        .replaceFirst("\\{}", "Long")
                        .replaceFirst("\\{}", cellTypeEnum.name())
                        .replaceFirst("\\{}", String.valueOf(rowNum))
                        .replaceFirst("\\{}", String.valueOf(columnIndex)));
            }
            return Double.valueOf(cell.getNumericCellValue()).longValue();
        }

        //Double
        if (propertyType==Double.class||propertyType==Double.class) {
            if (!cellTypeEnum.equals(CellType.NUMERIC)) {
                throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际类型:{}; row:{},column:{}"
                        .replaceFirst("\\{}", "Long")
                        .replaceFirst("\\{}", cellTypeEnum.name())
                        .replaceFirst("\\{}", String.valueOf(rowNum))
                        .replaceFirst("\\{}", String.valueOf(columnIndex)));
            }
            return cell.getNumericCellValue();
        }

        //日期处理(很重要)
        if (propertyType== Date.class) {
            if (!cellTypeEnum.equals(CellType.NUMERIC)) {
                throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际类型:{}; row:{},column:{}"
                        .replaceFirst("\\{}", "Long")
                        .replaceFirst("\\{}", cellTypeEnum.name())
                        .replaceFirst("\\{}", String.valueOf(rowNum))
                        .replaceFirst("\\{}", String.valueOf(columnIndex)));
            }
            if (cell instanceof HSSFCell) {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
            } else if (cell instanceof XSSFCell||cell instanceof SXSSFCell) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
            }
            throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际的Numeric不是日期类型; row:{},column:{}"
                    .replaceFirst("\\{}", "Date")
                    .replaceFirst("\\{}", String.valueOf(rowNum))
                    .replaceFirst("\\{}", String.valueOf(columnIndex)));
        }

        //String
        if (propertyType==String.class) {
//            if (!cellTypeEnum.equals(CellType.STRING)) {
//                throw new RuntimeException("数据类型不正确, 期望类型:{}, 实际类型:{}; row:{},column:{}"
//                        .replaceFirst("\\{}", "String")
//                        .replaceFirst("\\{}", cellTypeEnum.name())
//                        .replaceFirst("\\{}", String.valueOf(rowNum))
//                        .replaceFirst("\\{}", String.valueOf(columnIndex)));
//            }
//            return cell.getStringCellValue();

            //优化, string类型直接返回toString就好了
            return cell.toString();

        }
        throw new RuntimeException("数据类型不支持, 期望类型:{}, 实际类型:{}; row:{},column:{}"
                .replaceFirst("\\{}", propertyType.getCanonicalName())
                .replaceFirst("\\{}", cellTypeEnum.name())
                .replaceFirst("\\{}", String.valueOf(rowNum))
                .replaceFirst("\\{}", String.valueOf(columnIndex)));
    }
}
