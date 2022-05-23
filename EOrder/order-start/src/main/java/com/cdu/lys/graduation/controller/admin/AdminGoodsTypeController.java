package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsTypeBO;
import com.cdu.lys.graduation.admin.goods.service.AdminGoodsTypeService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeSearchQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeUpdateQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/24 23:08
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统商品类型接口")
@Slf4j
public class AdminGoodsTypeController {


    @Autowired
    private AdminGoodsTypeService adminGoodsTypeService;

    @Autowired
    private AdminGoodsTypeBO adminGoodsTypeBO;

    @ApiOperation("得到所有商品类型")
    @GetMapping(value = "/goods/type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<List<GoodsTypeDO>> getGoodsType() {
        List<GoodsTypeDO> goodsTypes = adminGoodsTypeBO.getAll();
        log.info("Admin select goods types success");
        return ActionResult.getSuccessResult(goodsTypes);
    }

    @ApiOperation("得到商品类型分页数据")
    @PostMapping(value = "/goods/type/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getGoodsType(@RequestBody @Valid PageQuery query) {
        PageResult<List<GoodsTypeDO>> goodsTypeList = adminGoodsTypeService.getGoodsTypeList(query);

        log.info("Admin select goods types success");
        return ActionResult.getSuccessResult(goodsTypeList);
    }

    @ApiOperation("更新商品类型")
    @PostMapping(value = "/goods/type/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> updateGoodsType(@RequestBody @Valid GoodsTypeUpdateQuery query) {
        int i = adminGoodsTypeBO.updateById(query);
        if (i > 0) {
            log.info("Admin update goods types success");
            return ActionResult.getSuccessResult("更新成功");
        }
        log.info("Admin update goods types failed");
        return ActionResult.getErrorResult("更新失败");
    }

    @ApiOperation("添加商品类型")
    @PostMapping(value = "/goods/type/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> addGoodsType(@RequestBody @Valid GoodsTypeQuery query) {
        int i = adminGoodsTypeBO.add(query);
        if (i > 0) {
            log.info("Admin add goods types success");
            return ActionResult.getSuccessResult("添加成功");
        }
        log.info("Admin add goods types failed");
        return ActionResult.getErrorResult("添加失败");
    }

    @ApiOperation("删除商品类型")
    @GetMapping(value = "/goods/type/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> deleteGoodsType(@NotNull(message = "id不能为null") Integer id) {
        int i = adminGoodsTypeBO.delete(id);
        if (i > 0) {
            log.info("Admin delete goods types success");
            return ActionResult.getSuccessResult("删除成功");
        }
        log.info("Admin delete goods types failed");
        return ActionResult.getErrorResult("删除失败");
    }

    @ApiOperation("搜索商品")
    @PostMapping(value = "/goods/type/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> searchGoodsType(@RequestBody @Valid GoodsTypeSearchQuery query) {

        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<GoodsTypeDO> search = adminGoodsTypeBO.searchByType(query.getType());
        PageResult<List<GoodsTypeDO>> pageResult = new PageResult<>(search);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        log.info("Admin search goodsType success");
        return ActionResult.getSuccessResult(pageResult);
    }
}
