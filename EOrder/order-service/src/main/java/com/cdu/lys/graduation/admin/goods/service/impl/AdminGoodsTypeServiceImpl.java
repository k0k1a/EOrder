package com.cdu.lys.graduation.admin.goods.service.impl;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsTypeBO;
import com.cdu.lys.graduation.admin.goods.service.AdminGoodsTypeService;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/24 23:15
 */
@Service
public class AdminGoodsTypeServiceImpl implements AdminGoodsTypeService {

    @Autowired
    private AdminGoodsTypeBO adminGoodsTypeBO;

    @Override
    public PageResult<List<GoodsTypeDO>> getGoodsTypeList(PageQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());

        List<GoodsTypeDO> goodsTypeDOList = adminGoodsTypeBO.getAll();

        PageResult<List<GoodsTypeDO>> pageResult = new PageResult<>(goodsTypeDOList);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }
}
