package com.nd.utils;

import com.nd.domain.PackTenant;

import org.junit.Test;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 服务开通
 * Created by Administrator on 2018/7/2 0002.
 */
public class TestServiceOpen {
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    @Test
    public void  testTenantOpen()
            throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException,
            IOException {
        ExcelHelper<PackTenant> areaExcelHelper = new ExcelHelper<PackTenant>(new DomainExtractor<PackTenant>(PackTenant.class) {
            @Override
            public Map<String, Integer> fieldNameMappingCellIndex() {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("orgName", 0);
                map.put("orgId", 1);
                return map;
            }
        });
        List<PackTenant> areas = areaExcelHelper.readExcel("D:\\a-test\\service-pack-open.xls");
        System.out.println(areas.size());
        System.out.println(areas);
        areaExcelHelper.writeXls("D:\\a-test\\service-pack-open-result.xls", areas, PackTenant.class);
    }

    public void reqPack(List<PackTenant> list) {
        OkHttpClient client = new OkHttpClient();
        for (PackTenant one : list) {
            Map<String, String> req = new HashMap<String, String>();
            req.put("app_id", "这是appId");
            req.put("service_tenant_id", String.valueOf(one.getOrgId()));
            req.put("app_name", "app-".concat(String.valueOf(one.getOrgName())));
            req.put("org_id", String.valueOf(one.getOrgId()));
            req.put("developer_uid", "207127");
            String json = JsonUtil.objToJson(req);
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("{packUrl}")
                    .addHeader("Authorization", "{bearerString}")
                    .addHeader("Content-Type","application/json")
                    .post(requestBody)
                    .build();
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                one.setSuccess(false);
                e.printStackTrace();
            }

        }
    }
    /**
     * 示例
     * @param args
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
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
        List<UID> areas = areaExcelHelper.readExcel("D:\\a-test\\ulist.xls");
        List list = new ArrayList();
        for (UID one : areas) {
            String uid = String.valueOf(one.getUid());
            list.add(uid);

        }
        System.out.println(list.size());
        System.out.println(list);
        areaExcelHelper.writeXls("D:\\a-test\\ulistCopy.xls", areas, UID.class);
    }
}
