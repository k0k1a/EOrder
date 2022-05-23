package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsOptionBO;
import com.cdu.lys.graduation.admin.goods.service.AdminGoodsOptionService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsOption;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.option.GoodsOptionQuery;
import com.cdu.lys.graduation.types.admin.goods.option.GoodsOptionSearchQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/26 19:15
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统商品规格接口")
@Slf4j
public class AdminGoodsOptionController {


    @Autowired
    private AdminGoodsOptionService adminGoodsOptionService;

    @Autowired
    private AdminGoodsOptionBO adminGoodsOptionBO;

    @PostMapping(value = "/goods/option/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("得到规格列表")
    public ActionResult<Object> getOptionsList(@RequestBody @Valid PageQuery query) {
        PageResult<List<AdminGoodsOption>> options = adminGoodsOptionService.getAllOptions(query);

        log.info("Admin select goodsOption success");
        return ActionResult.getSuccessResult(options);
    }

    @ApiOperation("搜索")
    @PostMapping(value = "/goods/option/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<AdminGoodsOption>>> search(@RequestBody @Valid GoodsOptionSearchQuery query) {
        PageResult<List<AdminGoodsOption>> options = adminGoodsOptionService.search(query);

        log.info("Admin select goodsOption success");
        return ActionResult.getSuccessResult(options);
    }

    @ApiOperation("添加选规格")
    @PostMapping(value = "/goods/option/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> addGoodsOption(@RequestBody @Valid GoodsOptionQuery query) {

        int i = adminGoodsOptionBO.addGoodsOption(query);
        log.info("Admin add goods type success");
        return ActionResult.getSuccessResult("成功");
    }
}
