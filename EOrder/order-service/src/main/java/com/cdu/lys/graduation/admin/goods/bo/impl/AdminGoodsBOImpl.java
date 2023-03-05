package com.cdu.lys.graduation.admin.goods.bo.impl;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsBO;
import com.cdu.lys.graduation.repository.dao.GoodsDOMapper;
import com.cdu.lys.graduation.repository.dao.GoodsPictureDOMapper;
import com.cdu.lys.graduation.repository.dao.ext.GoodsMapperExt;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsDOExample;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDO;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDOExample;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import com.cdu.lys.graduation.types.admin.goods.GoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.Picture;
import com.cdu.lys.graduation.types.admin.goods.UpdateGoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsSearchQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author liyinsong
 * @date 2022/3/19 11:37
 */
@Service
public class AdminGoodsBOImpl implements AdminGoodsBO {

    @Autowired
    private GoodsDOMapper goodsDOMapper;

    @Autowired
    private GoodsPictureDOMapper goodsPictureDOMapper;

    @Autowired
    private GoodsMapperExt goodsMapperExt;

    @Override
    public List<GoodsDO> selectGoods() {
        GoodsDOExample example = new GoodsDOExample();
        example.createCriteria()
                .andIsDeleteEqualTo("n");

        return goodsDOMapper.selectByExample(example);
    }

    @Override
    public List<GoodsPictureDO> getGoodsPicturesById(Integer goodsId) {
        GoodsPictureDOExample example = new GoodsPictureDOExample();
        example.createCriteria()
                .andGoodsIdEqualTo(goodsId)
                .andIsDeleteEqualTo("n");

        return goodsPictureDOMapper.selectByExample(example);
    }

    @Override
    public int deleteGoodsByGoodsId(Integer goodsId) {
        GoodsDOExample example = new GoodsDOExample();
        example.createCriteria()
                .andIdEqualTo(goodsId)
                .andIsDeleteEqualTo("n");

        GoodsDO goodsDO = new GoodsDO();
        goodsDO.setUpdateTime(new Date());
        goodsDO.setIsDelete("y");

        return goodsDOMapper.updateByExampleSelective(goodsDO, example);
    }

    @Override
    public int addGoods(GoodsQuery goods) {
        Date createTime = new Date();

        GoodsDO goodsDO = new GoodsDO();
        BeanUtils.copyProperties(goods, goodsDO);
        goodsDO.setCreateTime(createTime);
        goodsDO.setUpdateTime(createTime);

        goodsDOMapper.insertSelective(goodsDO);
        return goodsDO.getId();
    }

    @Override
    public int addGoodsPictures(List<Picture> pictures, Integer goodsId) {
        if (CollectionUtils.isEmpty(pictures)) {
            return 0;
        }
        Date createTime = new Date();
        pictures.forEach(picture -> {
            GoodsPictureDO goodsPictureDO = new GoodsPictureDO();
            BeanUtils.copyProperties(picture, goodsPictureDO);
            goodsPictureDO.setGoodsId(goodsId);
            goodsPictureDO.setCreateTime(createTime);
            goodsPictureDO.setUpdateTime(createTime);
            goodsPictureDOMapper.insertSelective(goodsPictureDO);
        });
        return 0;
    }

    @Override
    public int deleteGoodsPictures(Integer goodsId) {
        GoodsPictureDOExample example = new GoodsPictureDOExample();
        example.createCriteria()
                .andGoodsIdEqualTo(goodsId);
        GoodsPictureDO goodsPictureDO = new GoodsPictureDO();
        goodsPictureDO.setIsDelete(ExpireTypeEnum.YES.getType());
        goodsPictureDO.setUpdateTime(new Date());
        return goodsPictureDOMapper.updateByExampleSelective(goodsPictureDO, example);
    }

    @Override
    public int updateGoods(UpdateGoodsQuery goods) {
        GoodsDO goodsDO = new GoodsDO();
        BeanUtils.copyProperties(goods, goodsDO);
        goodsDO.setUpdateTime(new Date());

        return goodsDOMapper.updateByPrimaryKeySelective(goodsDO);
    }

    @Override
    public List<GoodsDO> search(GoodsSearchQuery query) {
        GoodsDOExample example = new GoodsDOExample();
        GoodsDOExample.Criteria criteria = example.createCriteria();

        if (StringUtils.hasLength(query.getGoodsName())) {
            criteria.andGoodsNameEqualTo(query.getGoodsName());
        }

        if (StringUtils.hasLength(query.getTaste())) {
            criteria.andTasteLike("%" + query.getTaste() + "%");
        }

        if (Objects.nonNull(query.getGoodsType())) {
            criteria.andGoodsTypeEqualTo(query.getGoodsType());
        }

        if (Objects.nonNull(query.getMinPrice())) {
            criteria.andPriceGreaterThan(query.getMinPrice());
        }

        if (Objects.nonNull(query.getMaxPrice())) {
            criteria.andPriceLessThan(query.getMaxPrice());
        }

        if (Objects.nonNull(query.getMinCost())) {
            criteria.andCostGreaterThan(query.getMinCost());
        }

        if (Objects.nonNull(query.getMaxCost())) {
            criteria.andCostLessThanOrEqualTo(query.getMaxCost());
        }

        criteria.andIsDeleteEqualTo("n");
        return goodsDOMapper.selectByExample(example);
    }

    @Override
    public int updateGoodsScore(Map<Integer, Double> scores) {
        if (CollectionUtils.isEmpty(scores)) {
            return 0;
        }
        return goodsMapperExt.updateScoreByGoodsIds(scores);
    }

}
