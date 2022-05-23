package com.cdu.lys.graduation.mini.goods.service.impl;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.goods.dto.*;
import com.cdu.lys.graduation.mini.goods.service.GoodsService;
import com.cdu.lys.graduation.repository.dao.GoodsDOMapper;
import com.cdu.lys.graduation.repository.dao.ext.GoodsMapperExt;
import com.cdu.lys.graduation.repository.dao.ext.GoodsTypeDOMapperExt;
import com.cdu.lys.graduation.repository.dao.ext.query.GoodsListParam;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.repository.entity.ext.Goods;
import com.cdu.lys.graduation.repository.entity.ext.GoodsList;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/2/8 10:15
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDOMapper goodsDOMapper;

    @Autowired
    private GoodsMapperExt goodsMapperExt;

    @Autowired
    private GoodsTypeDOMapperExt goodsTypeDOMapperExt;

    @Override
    public PageResult<List<GoodsListDTO>> selectGoodsList(Integer pageNo, Integer pageSize) {
        Page<Object> page = PageHelper.startPage(pageNo, pageSize);
        List<GoodsList> goodsLists = goodsMapperExt.selectGoodsList(null);
        return this.getPageResult(page, goodsLists);
    }

    @Override
    public List<GoodsTypeDTO> selectGoodsTypes() {
        List<GoodsTypeDO> goodsTypeDOS = goodsTypeDOMapperExt.selectAllGoodsType();

        List<GoodsTypeDTO> goodsTypeDTOS = new ArrayList<>();

        //深拷贝
        goodsTypeDOS.forEach((goodsTypeDO) -> {
            GoodsTypeDTO goodsTypeDTO = new GoodsTypeDTO();
            BeanUtils.copyProperties(goodsTypeDO, goodsTypeDTO);
            goodsTypeDTOS.add(goodsTypeDTO);
        });

        return goodsTypeDTOS;
    }

    @Override
    public PageResult<List<GoodsListDTO>> selectGoodsListByType(Integer pageNo, Integer pageSize, Integer goodsType) {
        Page<Object> page = PageHelper.startPage(pageNo, pageSize);
        GoodsListParam query = new GoodsListParam();
        query.setGoodsType(goodsType);

        List<GoodsList> goodsLists = goodsMapperExt.selectGoodsList(query);
        return this.getPageResult(page, goodsLists);
    }

    @Override
    public PageResult<List<GoodsListDTO>> selectHotGoodsList(PageQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<Goods> goods = goodsMapperExt.selectHotGoods();
        GoodsList goodsList = new GoodsList();
        goodsList.setType("热门商品");
        goodsList.setGoods(goods);

        List<GoodsList> goodsLists = new ArrayList<>();
        goodsLists.add(goodsList);
        return this.getPageResult(page, goodsLists);
    }

    @Override
    public GoodsDTO selectGoodsById(Integer id) {
        Goods goods = goodsMapperExt.selectGoodsById(id);
        GoodsDTO goodsDTO = convertGoods2GoodsDTO(goods);
        return goodsDTO;
    }

    @Override
    public List<GoodsDTO> searchGoods(String value) {
        List<Goods> goods = new ArrayList<>();

        //判断是否是按照类型搜索
        List<GoodsTypeDO> types = goodsTypeDOMapperExt.selectTypeLikeValue(value);
        if (!CollectionUtils.isEmpty(types)) {
            //按照类型搜索
            goods = goodsMapperExt.selectByGoodsType(types.get(0).getId());
        }

        //按照名称
        List<Goods> goodsByName = goodsMapperExt.selectAllLikeGoodsName(value);

        if (!CollectionUtils.isEmpty(goodsByName)) {
            goods.addAll(goodsByName);
        }

        //按照原料
        List<Goods> goodsByIngredients = goodsMapperExt.selectAllLikeIngredients(value);
        if (!CollectionUtils.isEmpty(goodsByIngredients)) {
            goods.addAll(goodsByIngredients);
        }

        //按照口味
        List<Goods> goodsByTaste = goodsMapperExt.selectAllLikeTaste(value);
        if (!CollectionUtils.isEmpty(goodsByTaste)) {
            goods.addAll(goodsByTaste);
        }

        //去重
        ArrayList<Goods> collect = goods.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(GoodsDO::getId))),
                        ArrayList::new));

        List<GoodsDTO> result = new ArrayList<>();

        collect.forEach(goodsDO -> {
            GoodsDTO goodsDTO = convertGoods2GoodsDTO(goodsDO);
            result.add(goodsDTO);
        });
        return result;
    }

    @Override
    public boolean buyGoodsById(Integer id, int number) {

        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(id);
        if (goodsDO.getStock() <= 0) {
            throw new BusinessException(ErrorType.BIZ_ERROR, goodsDO.getGoodsName() + "已卖完");
        }

        if (goodsDO.getStock() - number < 0) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "商品库存不足");
        }
        //减少库存，增加销量
        goodsMapperExt.decreaseGoodsStockById(id, number);
        return true;
    }

    /**
     * 得到页面对象
     *
     * @param page       分页插件分页对象
     * @param goodsLists 商品列表
     * @return 页面对象
     */
    private PageResult<List<GoodsListDTO>> getPageResult(Page<Object> page, List<GoodsList> goodsLists) {
        List<GoodsListDTO> goodsListDTOS = convertGoodsList2GoodsListDTO(goodsLists);

        PageResult<List<GoodsListDTO>> pageResult = new PageResult<>(goodsListDTOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    /**
     * 将数据库DO(Goods)转换为GoodsDTO
     *
     * @param goods GoodsDO
     * @return GoodsDTO
     */
    private GoodsDTO convertGoods2GoodsDTO(Goods goods) {
        GoodsDTO goodsDTO = new GoodsDTO();

        BeanUtils.copyProperties(goods, goodsDTO);

        //拷贝集合
        List<GoodsPictureDTO> pictures = new ArrayList<>();
        List<GoodsOptionDTO> goodsOptions = new ArrayList<>();

        //拷贝pictureDO->DTO
        goods.getPictures().forEach(picture -> {
            GoodsPictureDTO pictureDTO = new GoodsPictureDTO();
            BeanUtils.copyProperties(picture, pictureDTO);
            pictures.add(pictureDTO);
        });

        //拷贝optionDO->DTO
        goods.getGoodsOptions().forEach(goodsOption -> {
            GoodsOptionDTO goodsOptionDTO = new GoodsOptionDTO();
            List<GoodsOptionItemDTO> goodsOptionItemDTOS = new ArrayList<>();

            BeanUtils.copyProperties(goodsOption, goodsOptionDTO);

            goodsOption.getGoodsOptionItems().forEach(optionItem -> {
                GoodsOptionItemDTO goodsOptionItemDTO = new GoodsOptionItemDTO();
                BeanUtils.copyProperties(optionItem, goodsOptionItemDTO);
                goodsOptionItemDTOS.add(goodsOptionItemDTO);
            });
            goodsOptionDTO.setGoodsOptionItems(goodsOptionItemDTOS);
            goodsOptions.add(goodsOptionDTO);
        });

        goodsDTO.setPictures(pictures);
        goodsDTO.setGoodsOptions(goodsOptions);

        return goodsDTO;
    }

    /**
     * 将GoodsList转化为DTO对象
     *
     * @param goodsLists List<GoodsList>
     * @return GoodsListDTO
     */
    private List<GoodsListDTO> convertGoodsList2GoodsListDTO(List<GoodsList> goodsLists) {
        List<GoodsListDTO> goodsListDTOS = new ArrayList<>();

        //深拷贝
        for (GoodsList goodsList : goodsLists) {
            GoodsListDTO goodsListDTO = new GoodsListDTO();

            //先拷贝非集合数据
            BeanUtils.copyProperties(goodsList, goodsListDTO);
            //手动拷贝集合
            List<GoodsDTO> goodsDTOS = new ArrayList<>();

            for (Goods goods : goodsList.getGoods()) {
                GoodsDTO goodsDTO = convertGoods2GoodsDTO(goods);
                goodsDTOS.add(goodsDTO);
            }

            goodsListDTO.setGoods(goodsDTOS);
            goodsListDTOS.add(goodsListDTO);
        }

        return goodsListDTOS;
    }
}
