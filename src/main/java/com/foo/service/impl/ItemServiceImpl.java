package com.foo.service.impl;

import com.foo.*;
import com.foo.dto.ItemDTO;
import com.foo.enums.SkuTypeEnum;
import com.foo.service.ItemService;
import com.foo.utils.ListUtils;
import com.foo.utils.PriceUtils;
import com.foo.vo.ItemVO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    @Override
    public List<ItemVO> findBySkuIds(List<String> skuIds) {
        if (skuIds.size() > 100) {
            throw new RuntimeException("不能超过100个 skuId");
        }
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();
        ServiceBeanFactory serviceBeanFactory = ServiceBeanFactory.getInstance();

        SkuService skuService = serviceBeanFactory.getServiceBean(SkuService.class);

        List<ArrayList<String>> skuIdsList = ListUtils.spliList(skuIds, 20);
        //获取商品信息一次最多获取20条
        for(List<String> list:skuIdsList){
            List<SkuInfoDTO> skuInfoDTOList = skuService.findByIds(list);
            this.mergeList(skuInfoDTOList,itemDTOList);
        }

        PriceService priceService = serviceBeanFactory.getServiceBean(PriceService.class);
        InventoryService inventoryService = serviceBeanFactory.getServiceBean(InventoryService.class);
        //获取商品价格、库存
        for(ItemDTO itemDTO:itemDTOList){
            BigDecimal price = priceService.getBySkuId(itemDTO.getId());
            itemDTO.setPrice(price);

            List<ChannelInventoryDTO> inventoryDTOList = inventoryService.getBySkuId(itemDTO.getId());
            itemDTO.setInventory(inventoryDTOList);
        }

        //ITEM商品聚合汇总
        List<ItemVO> originItemVOList =  this.convergeList(itemDTOList,SkuTypeEnum.ORIGIN);
        List<ItemVO> digitalItemVOList =  this.convergeList(itemDTOList,SkuTypeEnum.DIGITAL);
        originItemVOList.addAll(digitalItemVOList);

        return originItemVOList;
    }

    /*
     商品信息复制：List<SkuInfoDTO> > List<ItemDTO>
     */
    private void  mergeList(List<SkuInfoDTO> skuInfoDTOList,List<ItemDTO> ItemDTOList){
        for(SkuInfoDTO skuInfoDTO:skuInfoDTOList){
            //这里可以：BeanUtils.copyProperties(A,B)字段复制 就不引入包了
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(skuInfoDTO.getId());
            itemDTO.setName(skuInfoDTO.getName());
            itemDTO.setSpuId(skuInfoDTO.getSpuId());
            itemDTO.setArtNo(skuInfoDTO.getArtNo());
            itemDTO.setSkuType(skuInfoDTO.getSkuType());
            ItemDTOList.add(itemDTO);
        }
    }

    /*
     聚合商品信息：List<SkuInfoDTO> > List<ItemVO>
     */
    private  List<ItemVO>  convergeList(List<ItemDTO> itemDTOList,SkuTypeEnum skuTypeEnum){
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        Collector<ItemDTO, ?, Map<String, List<ItemDTO>>> collect = null;
        if(SkuTypeEnum.ORIGIN==skuTypeEnum){
           collect = Collectors.groupingBy(ItemDTO::getArtNo);
        }else if(SkuTypeEnum.DIGITAL==skuTypeEnum) {
           collect = Collectors.groupingBy(ItemDTO::getSpuId);
        }
        itemDTOList.stream()
                .filter(a -> a.getSkuType().equals(skuTypeEnum.getType()))
                .collect(collect).forEach((k,v) -> {
            ItemDTO maxItemDTO = v.stream().collect(Collectors.maxBy(Comparator.comparing(ItemDTO::getPrice))).get();
            ItemDTO minItemDTO = v.stream().collect(Collectors.minBy(Comparator.comparing(ItemDTO::getPrice))).get();
            List<ChannelInventoryDTO> inventoryList = new ArrayList<ChannelInventoryDTO>();
            v.forEach((i) -> {
                inventoryList.addAll(i.getInventory());
            });
            BigDecimal inventorySum = inventoryList.stream().map(ChannelInventoryDTO::getInventory).reduce(BigDecimal.ZERO, BigDecimal::add);
            ItemVO itemVO = new ItemVO();
            itemVO.setName(maxItemDTO.getName());
            itemVO.setSkuType(maxItemDTO.getSkuType());
            itemVO.setArtNo(maxItemDTO.getArtNo());
            itemVO.setSpuId(maxItemDTO.getSpuId());
            itemVO.setPrice(PriceUtils.priceRange(minItemDTO.getPrice(),maxItemDTO.getPrice()));
            itemVO.setInventory(inventorySum);
            itemVOList.add(itemVO);
        });
        return itemVOList;
    }

}
