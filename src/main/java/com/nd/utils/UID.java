package com.nd.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by quanzongwei(207127) on 2018/1/12 0012.
 */
@Getter
@Setter
@Data
public class UID extends BaseDomain{
    Long uid;

    @Override
    public Object get$0() {
        return uid;
    }

    @Override public Object get$1() {
        return null;
    }

    @Override public Object get$2() {
        return null;
    }
}
