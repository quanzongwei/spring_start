package com.nd.model.excel;

import lombok.Data;

/**
 * @author BG388892
 * @date 2019/12/25
 */
@Data
public class Category {
    Long categoryId;
    Long storeId;
    Integer version;
    String creator;
    String updater;
    String categoryName;
    String categoryCode;
    Integer sortNumber;
}
