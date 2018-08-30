package com.foo.enums;

import lombok.Data;

/**
 * 商品类型枚举类
 * @author liudondong
 */
public enum SkuTypeEnum {

    ORIGIN("ORIGIN","原始商品"),
    DIGITAL("DIGITAL","数字化商品");

    private SkuTypeEnum(String type, String name){
        this.type =  type;
        this.name = name;
    }

    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
