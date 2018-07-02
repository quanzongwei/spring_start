package com.nd.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by quanzongwei(207127) on 2018/1/12 0012.
 */
@Getter
@Setter
public class UID extends BaseDomain{
    Long uid;

    @Override
    public Object get$0() {
        return uid;
    }
}
