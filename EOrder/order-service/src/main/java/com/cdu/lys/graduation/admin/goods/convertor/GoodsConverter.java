package com.cdu.lys.graduation.admin.goods.convertor;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsTypeBO;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsVO;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/3/26 16:41
 */
@Component
public class GoodsConverter {

    @Autowired
    private AdminGoodsTypeBO adminGoodsTypeBO;

    public PageResult<List<AdminGoodsVO>> convert2GoodsPageVo(PageResult<List<GoodsDO>> pageResult) {
        PageResult<List<AdminGoodsVO>> result = new PageResult<>();
        List<AdminGoodsVO> goodsVOS = new ArrayList<>();

        List<GoodsTypeDO> types = adminGoodsTypeBO.getAll();
        List<GoodsDO> goods = pageResult.getData();
        goods.forEach(goodsDO -> {
            AdminGoodsVO adminGoodsVO = new AdminGoodsVO();
            BeanUtils.copyProperties(goodsDO, adminGoodsVO);
            List<GoodsTypeDO> collect = types.stream()
                    .filter(goodsTypeDO -> goodsTypeDO.getId().equals(goodsDO.getGoodsType()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                adminGoodsVO.setGoodsType(collect.get(0).getType());
            }
            goodsVOS.add(adminGoodsVO);
        });

        BeanUtils.copyProperties(pageResult, result);
        result.setData(goodsVOS);
        return result;
    }
}
