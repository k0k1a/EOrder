package com.cdu.lys.graduation.controller.user;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.domain.comment.dto.CommentPictureDTO;
import com.cdu.lys.graduation.domain.comment.vo.CommentPictureVO;
import com.cdu.lys.graduation.domain.comment.vo.CommentVO;
import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.mini.user.service.CommentService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.user.CommentQuery;
import com.cdu.lys.graduation.types.user.CommentTypeQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/29 19:32
 */
@Api(tags = "User评论接口")
@RestController
@RequestMapping("/eorder/app/user")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisService redisService;

    @ApiOperation("用户评论")
    @PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> comment(@RequestBody @Valid CommentQuery query, @RequestHeader("token") String token) {
        //获取用户session_key
        LoginUser loginUser = redisService.getLoginValue(token);

        boolean result = commentService.comment(query, loginUser.getId());

        if (!result) {
            log.info("user comment error");
            return ActionResult.getErrorResult("评论失败");
        }
        redisService.incrDailyComment();
        log.info("user comment success");
        return ActionResult.getSuccessResult("评论成功");
    }

    @ApiOperation("评论列表")
    @PostMapping(value = "/comment/visit/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> commentList(@RequestBody @Valid PageQuery query) {

        PageResult<List<CommentDTO>> pageResult = commentService.commentList(query);
        PageResult<List<CommentVO>> result = this.convert2CommentPageVO(pageResult);

        log.info("select all comment list success");
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("获取用户评论列表")
    @PostMapping(value = "/comment/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<CommentVO>>> getUserCommentList(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        //获取用户session_key
        LoginUser loginUser = redisService.getLoginValue(token);
        PageResult<List<CommentDTO>> pageResult = commentService.getUserCommentList(query, loginUser.getId());

        PageResult<List<CommentVO>> result = this.convert2CommentPageVO(pageResult);

        log.info("select user comment list success");
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("评论分类搜索")
    @PostMapping(value = "/comment/visit/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getCommentListByType(@RequestBody @Valid CommentTypeQuery query) {

        PageResult<List<CommentDTO>> pageResult = commentService.commentListByType(query);
        PageResult<List<CommentVO>> result = this.convert2CommentPageVO(pageResult);

        log.info("select comment list by type[{}] success", query.getType());
        return ActionResult.getSuccessResult(result);
    }

    /**
     * 转为VO
     * @param pageResult DTO
     * @return VO
     */
    private PageResult<List<CommentVO>> convert2CommentPageVO(PageResult<List<CommentDTO>> pageResult) {

        PageResult<List<CommentVO>> result = new PageResult<>();
        List<CommentVO> commentVOS = new ArrayList<>();

        BeanUtils.copyProperties(pageResult, result);

        List<CommentDTO> data = pageResult.getData();
        if (CollectionUtils.isEmpty(data)) {
            return result;
        }

        data.forEach(commentDTO -> {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(commentDTO, commentVO);
            commentVO.setPicUrls(this.convert2PictureVO(commentDTO.getPicUrls()));
            commentVOS.add(commentVO);
        });
        result.setData(commentVOS);

        return result;
    }

    /**
     * 转为为vo List
     * @param commentPictureDTOS
     * @return
     */
    private List<CommentPictureVO> convert2PictureVO(List<CommentPictureDTO> commentPictureDTOS) {

        if (CollectionUtils.isEmpty(commentPictureDTOS)) {
            return null;
        }
        List<CommentPictureVO> res = new ArrayList<>();
        commentPictureDTOS.forEach(commentPictureDTO -> {
            CommentPictureVO commentPictureVO = new CommentPictureVO();
            commentPictureVO.setPicUrl(commentPictureDTO.getPicUrl());
            res.add(commentPictureVO);
        });
        return res;
    }
}
