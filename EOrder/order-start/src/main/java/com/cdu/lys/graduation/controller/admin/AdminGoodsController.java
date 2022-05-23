package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.goods.bo.AdminGoodsBO;
import com.cdu.lys.graduation.admin.goods.convertor.GoodsConverter;
import com.cdu.lys.graduation.admin.goods.service.AdminGoodsService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsPictureVO;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsVO;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.GoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.UpdateGoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsSearchQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/21 21:58
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统商品接口")
@Slf4j
public class AdminGoodsController {

    @Autowired
    private AdminGoodsService adminGoodsService;

    @Autowired
    private AdminGoodsBO adminGoodsBO;

    @Autowired
    private GoodsConverter goodsConverter;

    @ApiOperation(value = "商品列表")
    @PostMapping(value = "/goods/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getGoodsList(@RequestBody @Valid PageQuery query) {
        PageResult<List<GoodsDO>> pageResult = adminGoodsService.getGoodsList(query);

        PageResult<List<AdminGoodsVO>> result = goodsConverter.convert2GoodsPageVo(pageResult);
        log.info("Admin select goods list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation(value = "删除商品")
    @GetMapping(value = "/goods/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> deleteGoods(Integer goodsId) {
        boolean res = adminGoodsService.deleteGoods(goodsId);

        log.info("Admin delete goods:[{}] success", goodsId);
        return ActionResult.getSuccessResult("删除成功");
    }

    @ApiOperation(value = "添加商品")
    @PostMapping(value = "/goods/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Integer> addGoods(@RequestBody @Valid GoodsQuery goods) {
        int id = adminGoodsService.addGoods(goods);
        log.info("Admin add goods success,id:{}", id);
        return ActionResult.getSuccessResult("添加成功");
    }

    @PostMapping(value = "/goods/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> upload() {
        log.info("11111111111111");
        return ActionResult.getSuccessResult("成功");
    }

    @ApiOperation(value = "修改商品")
    @PostMapping(value = "/goods/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "Goods:ID", key = "#goods.id")
    public ActionResult<Object> updateGoods(@RequestBody @Valid UpdateGoodsQuery goods) {
        int i = adminGoodsService.updateGoods(goods);
        log.info("Admin update goods success, rows:{}", i);
        return ActionResult.getSuccessResult("更新成功");
    }

    @ApiOperation("搜索")
    @PostMapping(value = "/goods/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<AdminGoodsVO>>> search(@RequestBody @Valid GoodsSearchQuery query) {
        PageResult<List<GoodsDO>> search = adminGoodsService.search(query);

        PageResult<List<AdminGoodsVO>> result = goodsConverter.convert2GoodsPageVo(search);
        log.info("Admin search goods list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("获取商品图片")
    @GetMapping(value = "/goods/picture/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getGoodsPictures(@NotNull(message = "商品id不能为空") Integer goodsId) {
        List<GoodsPictureDO> goodsPictures = adminGoodsBO.getGoodsPicturesById(goodsId);

        List<AdminGoodsPictureVO> goodsPictureVOS = new ArrayList<>();
        goodsPictures.forEach(goodsPictureDO -> {
            AdminGoodsPictureVO goodsPictureVO = new AdminGoodsPictureVO();
            BeanUtils.copyProperties(goodsPictureDO, goodsPictureVO);
            goodsPictureVOS.add(goodsPictureVO);
        });
        log.info("Admin get goods picture success");
        return ActionResult.getSuccessResult(goodsPictureVOS);
    }
}
