package com.nd.domain;

import com.nd.utils.BaseDomain;
import lombok.Data;

/**
 * Created by Administrator on 2018/7/2 0002.
 */
@Data
public class PackTenant extends BaseDomain {

    private String orgName;

    private Long orgId;

    private boolean isSuccess = true;

    @Override
    public Object get$0() {
        return orgName;
    }

    @Override
    public Object get$1() {
        System.out.println("你好世界");
        return orgId;

    }

    @Override
    public Object get$2() {
        return isSuccess;
    }
}
