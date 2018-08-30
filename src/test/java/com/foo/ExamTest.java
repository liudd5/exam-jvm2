package com.foo;

import com.foo.service.ItemService;
import com.foo.service.impl.ItemServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExamTest {

    private static List<String> skuIds;

    /**
     * 构造100个 skuid 作为测试条件
     */
    @BeforeClass
    public static void setUp() {
        skuIds = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            skuIds.add(String.valueOf(i));
        }
    }

    @AfterClass
    public static void tearDown() {
        skuIds = null;
    }


    /**
     * 测试入口
     */
    @Test
    public void test(){
        ItemService itemService = new ItemServiceImpl();
        itemService.findBySkuIds(skuIds).forEach(itemVO -> {
            System.out.println(itemVO);
        });
    }

}
