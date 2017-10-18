package com.nd.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.apache.poi.ss.usermodel.Row;

/**
 * Created by quanzongwei(207127) on 2017/8/7 0007.
 */
public interface ExcelExtractor<T> {
   T extractor(Row XSSFRow)
           throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException;
}
