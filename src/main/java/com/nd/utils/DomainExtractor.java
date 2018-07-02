package com.nd.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by quanzongwei(207127) on 2017/8/7 0007.
 */
@Getter
@Setter
public abstract class DomainExtractor<T> implements ExcelExtractor<T> {
    Class<T> tClass;

    Map<String, Integer> mapping;

    public DomainExtractor(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T extractor(Row row) throws IllegalAccessException, InstantiationException, IntrospectionException,
            InvocationTargetException {
        if (this.mapping == null) {
            this.mapping = fieldNameMappingCellIndex();
        }
        Object one = this.gettClass().newInstance();
        doMapping(one, row);
        return (T) one;

    }

    /**
     * 模板方法,设置java domain的字段和excel表格中cell的映射关系
     */
    public abstract Map<String, Integer> fieldNameMappingCellIndex();

    public Class<T> gettClass() {
        return tClass;
    }

    /**
     * 根据mapping映射规则,把excel中的数据映射到domain中
     * created by quanzongwei
     */
    public void doMapping(Object one, Row row) throws IntrospectionException, InvocationTargetException,
            IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(one.getClass(), Object.class);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (mapping.keySet().contains(pd.getName())) {
                Object value = getValueByPropertyValueAndCell(pd, row.getCell(mapping.get(pd.getName())));
                pd.getWriteMethod().invoke(one, value);
                pd.getPropertyType().getAnnotations();
            }
        }
    }

    /**
     * 目前数据映射支持String和Integer类型
     */
    public Object getValueByPropertyValueAndCell(PropertyDescriptor pd, Cell cell) {
        if (pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class) {
            return (int) cell.getNumericCellValue();
        }
        if (pd.getPropertyType() == String.class) {
            return cell.getStringCellValue();
        }
        if (pd.getPropertyType() == Long.class) {
            return Long.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue());
        }
        throw new RuntimeException("数据类型不支持");
    }
}
