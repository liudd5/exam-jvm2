package com.foo.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * List价格工具类
 * @author liudondong
 */
public class PriceUtils {

    /**
     * 价格区间生成
     * @param minPrice 最大金额
     * @param maxPrice 最小金额
     * @return
     */
    public static String  priceRange(BigDecimal minPrice, BigDecimal maxPrice){
        if(minPrice==null||minPrice.intValue()==0){
            return maxPrice.intValue()+"";
        }
        if(minPrice.equals(maxPrice)){
            return  maxPrice.intValue()+"";
        }
        if(minPrice!=(maxPrice)){
            return minPrice.intValue()+"~" +maxPrice.intValue();
        }
        return "";
    }
}
