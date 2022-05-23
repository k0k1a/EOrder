package com.cdu.lys.graduation.admin.goods.bo.impl;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsTypeBO;
import com.cdu.lys.graduation.repository.dao.GoodsTypeDOMapper;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDOExample;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeUpdateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/24 23:11
 */
@Service
public class AdminGoodsTypeBOImpl implements AdminGoodsTypeBO {

    @Autowired
    private GoodsTypeDOMapper goodsTypeDOMapper;

    @Override
    public List<GoodsTypeDO> getAll() {
        GoodsTypeDOExample example = new GoodsTypeDOExample();
        example.createCriteria()
                .andIsDeleteEqualTo("n");

        return goodsTypeDOMapper.selectByExample(example);
    }

    @Override
    public int updateById(GoodsTypeUpdateQuery query) {
        GoodsTypeDO goodsTypeDO = new GoodsTypeDO();
        goodsTypeDO.setId(query.getId());
        goodsTypeDO.setUpdateTime(new Date());
        goodsTypeDO.setType(query.getType());

        return goodsTypeDOMapper.updateByPrimaryKeySelective(goodsTypeDO);
    }

    @Override
    public int add(GoodsTypeQuery query) {
        GoodsTypeDO goodsTypeDO = new GoodsTypeDO();
        goodsTypeDO.setCreateTime(new Date());
        goodsTypeDO.setType(query.getType());

        return goodsTypeDOMapper.insertSelective(goodsTypeDO);
    }

    @Override
    public int delete(Integer id) {
        GoodsTypeDOExample example = new GoodsTypeDOExample();
        example.createCriteria()
                .andIdEqualTo(id);
        GoodsTypeDO goodsTypeDO = new GoodsTypeDO();
        goodsTypeDO.setUpdateTime(new Date());
        goodsTypeDO.setIsDelete("y");

        return goodsTypeDOMapper.updateByExampleSelective(goodsTypeDO, example);
    }

    @Override
    public List<GoodsTypeDO> searchByType(String type) {
        GoodsTypeDOExample example = new GoodsTypeDOExample();
        example.createCriteria()
                .andTypeEqualTo(type);

        return goodsTypeDOMapper.selectByExample(example);
    }

}
