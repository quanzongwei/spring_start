package com.nd.model.excel;

import lombok.Data;

/**
 * @author BG388892
 * @date 2019/12/25
 */
@Data
public class CategoryRelation {
    Long relationId;
    Integer version;
    String creator;
    String updater;
    Long categoryId;
    Integer sortNumber;
    Long storeId;
    Long baseSkuId;
}
