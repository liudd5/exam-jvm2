package com.foo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * List操作工具类
 * @author liudondong
 */
public class ListUtils {

    /**
     * 集合分割
     * @param lists 分割集合
     * @param spliSize 分割数
     * @return
     */
    public static List<ArrayList<String>> spliList(List<String> lists,int spliSize){
        if (lists==null||lists.isEmpty()) {
            throw new RuntimeException("lists 入参不能为空");
        }

        if (spliSize<=0) {
            throw new RuntimeException("spliSize 入参不合法");
        }

        List spliList = new ArrayList<ArrayList<String>>();
        int size = lists.size();
        int num = size/spliSize;
        int end;
        for (int i=0;i<num;i++){
            end = (i+1)*spliSize;
            if(end>=size) end = size;
            spliList.add(lists.subList(i*spliSize,end));
        }
        return spliList;
    }
}
