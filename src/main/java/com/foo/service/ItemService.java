package com.foo.service;


import com.foo.dto.ItemDTO;
import com.foo.vo.ItemVO;

import java.util.List;

/**
 * 商品聚合服务
 * @author liudondong
 */
public interface ItemService {

    /**
     * 传入一组 SKUId(入参限定不能超过100个),  输出一组 item, 并包含总库存和区间价.
     * @param skuIds 不能超过100个
     * @return
     */
    List<ItemVO> findBySkuIds(List<String> skuIds);

}
