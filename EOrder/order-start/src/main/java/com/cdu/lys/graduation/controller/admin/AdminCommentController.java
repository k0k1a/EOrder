package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.comment.bo.AdminCommentBO;
import com.cdu.lys.graduation.admin.comment.service.AdminCommentService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.mini.user.service.CommentService;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.comment.CommentReplyQuery;
import com.cdu.lys.graduation.types.admin.comment.CommentSearchQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/2 10:27
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统评论接口")
@Validated
@Slf4j
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AdminCommentService adminCommentService;

    @Autowired
    private AdminCommentBO adminCommentBO;

    @ApiOperation("获取用户评论列表")
    @PostMapping(value = "/comment/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> commentList(@RequestBody @Valid PageQuery query) {
        PageResult<List<CommentDTO>> pageResult = commentService.commentList(query);

        log.info("Admin select all comment list success");
        return ActionResult.getSuccessResult(pageResult);
    }

    @ApiOperation("按用户账号搜索")
    @PostMapping(value = "/comment/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> search(@RequestBody @Valid CommentSearchQuery query) {
        PageResult<List<CommentDTO>> pageResult = adminCommentService.search(query);

        log.info("Admin search user:{} comment list success", query.getUsername());
        return ActionResult.getSuccessResult(pageResult);
    }

    @ApiOperation("回复")
    @PostMapping(value = "/comment/reply", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> reply(@RequestBody @Valid CommentReplyQuery query) {
        boolean reply = adminCommentBO.reply(query);
        if (!reply) {
            return ActionResult.getErrorResult("回复失败");
        }
        return ActionResult.getSuccessResult("回复成功");
    }

    @ApiOperation("删除评论")
    @GetMapping(value = "/comment/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> delete(@NotNull(message = "id不能为空") Integer id) {
        boolean delete = adminCommentBO.delete(id);
        if (!delete) {
            return ActionResult.getErrorResult("删除失败");
        }
        return ActionResult.getSuccessResult("删除成功");
    }

}
