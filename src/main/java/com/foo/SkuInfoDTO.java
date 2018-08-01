package com.foo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuInfoDTO implements Serializable{
    /**
     * SKU id
     */
    private String id;
    /**
     * SKU 名称
     */
    private String name;
    /**
     * 货号
     */
    private String artNo;
    /**
     * itemid
     */
    private String itemId;
    /**
     * 商品类型, 原始商品:ORIGIN; ITEM商品: ITEM
     */
    private String skuType;
}
