package com.cdu.lys.graduation.admin.goods.service.impl;

import com.cdu.lys.graduation.admin.comment.bo.AdminCommentBO;
import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsBO;
import com.cdu.lys.graduation.admin.goods.service.AdminGoodsService;
import com.cdu.lys.graduation.admin.order.bo.AdminOrderBO;
import com.cdu.lys.graduation.admin.order.bo.AdminOrderFormInfoBO;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.commons.utils.DateUtils;
import com.cdu.lys.graduation.repository.entity.CommentDO;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.GoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.Picture;
import com.cdu.lys.graduation.types.admin.goods.UpdateGoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsSearchQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/3/19 11:33
 */
@Service
public class AdminGoodsServiceImpl implements AdminGoodsService {

    @Autowired
    private AdminGoodsBO adminGoodsBO;

    @Autowired
    private AdminOrderFormInfoBO adminOrderFormInfoBO;

    @Autowired
    private AdminCommentBO adminCommentBO;

    @Autowired
    private AdminOrderBO adminOrderBO;

    @Override
    public PageResult<List<GoodsDO>> getGoodsList(PageQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());

        List<GoodsDO> goodsDOS = adminGoodsBO.selectGoods();

        PageResult<List<GoodsDO>> pageResult = new PageResult<>(goodsDOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public boolean deleteGoods(Integer goodsId) {
        int i = adminGoodsBO.deleteGoodsByGoodsId(goodsId);
        adminGoodsBO.deleteGoodsPictures(goodsId);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addGoods(GoodsQuery goods) {

        int goodsId = adminGoodsBO.addGoods(goods);

        List<Picture> pictures = goods.getPictures();
        //????????????
        adminGoodsBO.addGoodsPictures(pictures, goodsId);

        return goodsId;
    }

    @Override
    public int updateGoods(UpdateGoodsQuery goods) {
        int i = adminGoodsBO.updateGoods(goods);
        List<Picture> pictures = goods.getPictures();
        if (!CollectionUtils.isEmpty(pictures)) {
            //????????????
            Integer goodsId = goods.getId();
            adminGoodsBO.deleteGoodsPictures(goodsId);
            adminGoodsBO.addGoodsPictures(pictures, goodsId);
        }
        return i;
    }

    @Override
    public PageResult<List<GoodsDO>> search(GoodsSearchQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());

        List<GoodsDO> goodsDOS = adminGoodsBO.search(query);

        PageResult<List<GoodsDO>> pageResult = new PageResult<>(goodsDOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public int calculateGoodsScore() {
        Date monthBefore = DateUtils.add(new Date(), Calendar.MONTH, -1);
        List<CommentDO> commentDOList = adminCommentBO.getCommentBeforeDate(monthBefore);
        List<OrderFormDO> orderBeforeMonth = adminOrderBO.getOrderBeforeDate(monthBefore);
        //?????????????????????????????????????????????
        List<String> collect = orderBeforeMonth.stream()
                .filter(orderFormDO -> ExpireTypeEnum.YES.getType().equals(orderFormDO.getIsComment()))
                .map(OrderFormDO::getOrderNum).collect(Collectors.toList());
        //??????????????????????????????
        List<OrderFormInfoDO> orderInfoList = adminOrderFormInfoBO.getOrderInfoOrderNumIn(collect);

        //????????????????????????????????????key: orderNum, v: sum(score)
        Map<String, Integer> commentMap = commentDOList.stream()
                .collect(Collectors.toMap(
                        CommentDO::getOrderNum,
                        commentDO -> commentDO.getServiceScore() + commentDO.getTasteScore() + commentDO.getEnvironmentScore()
                ));

        //?????????????????????????????????k???goodsId???v???sum(score),cnt
        Map<Integer, Count> goodsScoreMap = new HashMap<>();
        orderInfoList.forEach(orderFormInfoDO -> {
            Integer goodsId = orderFormInfoDO.getGoodsId();
            int score = commentMap.get(orderFormInfoDO.getOrderNum()) == null ? 0 : commentMap.get(orderFormInfoDO.getOrderNum());

            Count temp;
            if ((temp = goodsScoreMap.get(goodsId)) != null) {
                temp.setCounts(temp.getCounts() + 1);
                temp.setScore(temp.getScore() + score);
            } else {
                temp = new Count();
                temp.setCounts(1);
                temp.setScore(score);
            }
            goodsScoreMap.put(goodsId, temp);
        });

        DecimalFormat format = new DecimalFormat("0.0");
        for (Map.Entry<Integer, Count> entry : goodsScoreMap.entrySet()) {
            Count value = entry.getValue();
            //???????????????5??????
            double rate = (value.getScore() * 1.0 / (value.getCounts() * 15)) * 5;
            value.setRate(Double.parseDouble(format.format(rate)));
        }

        Map<Integer, Double> scoreMap = new HashMap<>();
        goodsScoreMap.forEach((k, v) -> scoreMap.put(k, v.getRate()));
        //??????????????????
        return adminGoodsBO.updateGoodsScore(scoreMap);
    }
}

@Data
class Count {

    private int score;

    private int counts;

    private double rate;
}