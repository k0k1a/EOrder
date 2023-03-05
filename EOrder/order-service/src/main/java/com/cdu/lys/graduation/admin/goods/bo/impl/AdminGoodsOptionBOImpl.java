package com.cdu.lys.graduation.admin.goods.bo.impl;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsOptionBO;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsOption;
import com.cdu.lys.graduation.repository.dao.GoodsDOMapper;
import com.cdu.lys.graduation.repository.dao.GoodsOptionDOMapper;
import com.cdu.lys.graduation.repository.dao.GoodsOptionItemDOMapper;
import com.cdu.lys.graduation.repository.entity.*;
import com.cdu.lys.graduation.types.admin.goods.option.GoodsOptionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/26 19:27
 */
@Service
public class AdminGoodsOptionBOImpl implements AdminGoodsOptionBO {

    @Autowired
    private GoodsOptionDOMapper goodsOptionDOMapper;

    @Autowired
    private GoodsOptionItemDOMapper goodsOptionItemDOMapper;

    @Autowired
    private GoodsDOMapper goodsDOMapper;

    @Override
    public List<AdminGoodsOption> selectAll() {

        GoodsDOExample example = new GoodsDOExample();
        example.createCriteria()
                .andIsDeleteEqualTo("n");


        List<GoodsDO> goodsDOS = goodsDOMapper.selectByExample(example);

        return this.getResult(goodsDOS);
    }

    @Override
    public List<AdminGoodsOption> search(String goodsName) {
        GoodsDOExample example = new GoodsDOExample();
        example.createCriteria()
                .andGoodsNameEqualTo(goodsName)
                .andIsDeleteEqualTo("n");

        List<GoodsDO> goodsDOS = goodsDOMapper.selectByExample(example);

        return this.getResult(goodsDOS);
    }

    /**
     * 得到结果
     * @param goodsDOS 商品列表
     * @return 结果
     */
    private List<AdminGoodsOption> getResult(List<GoodsDO> goodsDOS) {
        if (CollectionUtils.isEmpty(goodsDOS)) {
            return null;
        }
        List<AdminGoodsOption> result = new ArrayList<>();

        goodsDOS.forEach(goodsDO -> {
            AdminGoodsOption adminGoodsOption = new AdminGoodsOption();

            GoodsOptionDOExample optionDOExample = new GoodsOptionDOExample();
            GoodsOptionDOExample.Criteria criteria = optionDOExample.createCriteria();
            criteria.andGoodsIdEqualTo(goodsDO.getId())
                    .andIsDeleteEqualTo("n");

            BeanUtils.copyProperties(goodsDO, adminGoodsOption);
            List<GoodsOptionDO> goodsOptionDOS = goodsOptionDOMapper.selectByExample(optionDOExample);
            adminGoodsOption.setGoodsId(goodsDO.getId());
            adminGoodsOption.setGoodsOptionText(this.getOptionText(goodsOptionDOS));

            result.add(adminGoodsOption);
        });
        return result;
    }

    @Override
    public int addGoodsOption(GoodsOptionQuery query) {

        GoodsOptionDO goodsOptionDO = new GoodsOptionDO();
        goodsOptionDO.setGoodsId(query.getGoodsId());
        goodsOptionDO.setOptionName(query.getOptionName());
        Date createTime = new Date();
        goodsOptionDO.setCreateTime(createTime);
        goodsOptionDO.setUpdateTime(createTime);

        int i = goodsOptionDOMapper.insertSelective(goodsOptionDO);

        query.getOptionItems().forEach(item->{
            GoodsOptionItemDO record = new GoodsOptionItemDO();
            record.setOptionId(goodsOptionDO.getId());
            if (item.getExtraPrice() != null) {
                record.setExtraPrice(item.getExtraPrice());
            }
            if (StringUtils.hasLength(item.getOptionItem())) {
                record.setOptionItem(item.getOptionItem());
                record.setCreateTime(createTime);
                record.setUpdateTime(createTime);
                goodsOptionItemDOMapper.insertSelective(record);
            }
        });

        return i;
    }

    private String getOptionText(List<GoodsOptionDO> goodsOptionDOS) {

        StringBuilder sb = new StringBuilder();

        for (GoodsOptionDO goodsOptionDO : goodsOptionDOS) {
            sb.append(goodsOptionDO.getOptionName());
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
