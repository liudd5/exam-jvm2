package com.foo.vo;

import com.foo.ChannelInventoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO implements Serializable{
    private static final long serialVersionUID = -3716616385982084898L;
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
    private String spuId;
    /**
     * 商品类型, 原始商品:ORIGIN; 数字化商品: DIGITAL
     */
    private String skuType;
    /**
     * 渠道库存, 保留小数点后2位
     */
    private BigDecimal inventory;
    /**
     * 价格范围
     */
    private String price;
}
