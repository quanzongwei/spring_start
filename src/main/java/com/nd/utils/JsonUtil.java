package com.nd.utils;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2016     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2016年11月25日           </p>
 * @author Linhua(831008)
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public final class JsonUtil {

    // private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> T jsonParseObj(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return WafJsonMapper.parse(jsonString, clazz);
        } catch (Exception e) {
            // LOGGER.error("parse json <" + jsonString + "> error!", e);
            return null;
        }
    }

    public static <T> T jsonParseCollection(String jsonString, Class<?> collectionClass, Class<?> elementClass) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        JavaType javaType = WafJsonMapper.getMapper().getTypeFactory()
                .constructParametricType(collectionClass, elementClass);
        try {
            return WafJsonMapper.getMapper().readValue(jsonString, javaType);
        } catch (IOException e) {
            // LOGGER.error("parse collection json <" + jsonString + "> error!", e);
            return null;
        }
    }

    public static <T> T jsonParseCollection(byte[] json, Class<?> collectionClass, Class<?> elementClass) {
        if (ArrayUtils.isEmpty(json)) {
            return null;
        }
        JavaType javaType = WafJsonMapper.getMapper().getTypeFactory()
                .constructParametricType(collectionClass, elementClass);
        try {
            return WafJsonMapper.getMapper().readValue(json, javaType);
        } catch (IOException e) {
            // LOGGER.error("parse collection json <" + new String(json) + "> error!", e);
            return null;
        }
    }

    public static String objToJson(Object obj) {
        try {
            return WafJsonMapper.toJson(obj);
        } catch (Exception e) {
            // LOGGER.error("Obj to json error!", e);
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        return WafJsonMapper.getMapper();
    }

    public static <T> T parse(String json, Class<T> objectType) throws IOException {
        return WafJsonMapper.parse(json, objectType);
    }

    public static String toJson(Object obj) throws IOException {
        return WafJsonMapper.toJson(obj);
    }

}
