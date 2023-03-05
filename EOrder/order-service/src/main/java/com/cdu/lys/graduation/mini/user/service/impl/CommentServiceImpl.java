package com.cdu.lys.graduation.mini.user.service.impl;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.mini.order.bo.OrderFormBO;
import com.cdu.lys.graduation.mini.user.bo.CommentBO;
import com.cdu.lys.graduation.mini.user.service.CommentService;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.payment.TradeStatus;
import com.cdu.lys.graduation.types.user.CommentQuery;
import com.cdu.lys.graduation.types.user.CommentTypeQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/29 19:33
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentBO commentBO;

    @Autowired
    private OrderFormBO orderFormBO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean comment(CommentQuery query, Integer userId) {
        String orderNum = query.getOrderNum();
        OrderFormDO orderFormDO = orderFormBO.selectByOrderNum(orderNum);

        //已经评论、交易未完成
        if (ExpireTypeEnum.YES.getType().equals(orderFormDO.getIsComment())
                && !TradeStatus.FINISHED.getCode().equals(orderFormDO.getTradeStatus())) {
            return false;
        }

        int id = commentBO.saveComment(query, userId);
        commentBO.savePicture(query.getPics(), id);

        OrderFormDO update = new OrderFormDO();
        update.setIsComment(ExpireTypeEnum.YES.getType());
        update.setCommentTime(new Date());
        this.orderFormBO.updateByOrderNumSelective(update, orderNum);

        return true;
    }

    @Override
    public PageResult<List<CommentDTO>> commentList(PageQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<CommentDTO> allComment = commentBO.getAllComment();

        PageResult<List<CommentDTO>> pageResult = new PageResult<>(allComment);

        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public PageResult<List<CommentDTO>> getUserCommentList(PageQuery query, Integer userId) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<CommentDTO> allComment = commentBO.getUserComment(userId);

        PageResult<List<CommentDTO>> pageResult = new PageResult<>(allComment);

        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public PageResult<List<CommentDTO>> commentListByType(CommentTypeQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<CommentDTO> allComment = commentBO.getCommentByType(query.getType());

        PageResult<List<CommentDTO>> pageResult = new PageResult<>(allComment);

        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }
}
