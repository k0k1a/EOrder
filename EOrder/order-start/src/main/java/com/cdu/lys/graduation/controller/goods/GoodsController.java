package com.cdu.lys.graduation.controller.goods;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.goods.dto.GoodsDTO;
import com.cdu.lys.graduation.domain.goods.dto.GoodsListDTO;
import com.cdu.lys.graduation.domain.goods.dto.GoodsOptionDTO;
import com.cdu.lys.graduation.domain.goods.dto.GoodsTypeDTO;
import com.cdu.lys.graduation.domain.goods.vo.*;
import com.cdu.lys.graduation.mini.goods.service.GoodsService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.ParamException;
import com.cdu.lys.graduation.types.goods.GoodsListQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author liyinsong
 * @date 2022/2/8 11:49
 */
@RestController
@Api(tags = "Goods接口")
@RequestMapping("/eorder/app/goods")
@Validated
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @ApiOperation("查询商品列表list")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "Goods:List", key = "#pageQuery.pageNo+''+#pageQuery.pageSize")
    public ActionResult<PageResult<List<GoodsListVO>>> selectGoodsList(@RequestBody @Valid PageQuery pageQuery) {
        PageResult<List<GoodsListDTO>> goodsList = goodsService.selectGoodsList(pageQuery.getPageNo(), pageQuery.getPageSize());
        PageResult<List<GoodsListVO>> result = this.getGoodsListPageVO(goodsList);

        log.info("select goods list success, PageQuery:[ {} ]", pageQuery);
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("查询所有商品类型")
    @GetMapping(value = "/type/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "Goods:Types")
    public ActionResult<List<GoodsTypeVO>> selectGoodsTypes() {
        List<GoodsTypeDTO> goodsTypeDTOS = goodsService.selectGoodsTypes();

        List<GoodsTypeVO> goodsTypeVOS = new ArrayList<>();
        goodsTypeDTOS.forEach(goodsTypeDTO -> {
            GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
            BeanUtils.copyProperties(goodsTypeDTO, goodsTypeVO);
            goodsTypeVOS.add(goodsTypeVO);
        });
        log.info("select all goods type success");

        return ActionResult.getSuccessResult(goodsTypeVOS);
    }

    @ApiOperation("根据Query查询GoodsList")
    @PostMapping(value = "/listByType", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "Goods:List:Type", key = "#query.pageNo+''+#query.pageSize+''+#query.goodsType")
    public ActionResult<PageResult<List<GoodsListVO>>> selectGoodsListByType(@RequestBody @Valid GoodsListQuery query) {
        PageResult<List<GoodsListDTO>> goodsList = goodsService.selectGoodsListByType(query.getPageNo(), query.getPageSize(), query.getGoodsType());
        PageResult<List<GoodsListVO>> result = this.getGoodsListPageVO(goodsList);

        log.info("select goods list by goodsType success, query is[ {} ]", query);
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("查询热门商品list")
    @PostMapping(value = "/list/hot", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "Goods:List:Hot", key = "#query.pageNo+''+#query.pageSize")
    public ActionResult<PageResult<List<GoodsListVO>>> getHotGoodsList(@RequestBody @Valid PageQuery query) {
        PageResult<List<GoodsListDTO>> goodsList = goodsService.selectHotGoodsList(query);
        PageResult<List<GoodsListVO>> result = this.getGoodsListPageVO(goodsList);

        log.info("select hot goods list success, query is[ {} ]", query);
        return ActionResult.getSuccessResult(result);
    }

    @GetMapping(value = "/getGoods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("根据id查询goods")
    @Cacheable(value = "Goods:ID", key = "#goodsId")
    public ActionResult<GoodsVO> selectGoodsInfo(Integer goodsId) {
        GoodsDTO goodsDTO = goodsService.selectGoodsById(goodsId);
        log.info("select Goods by id success, goods_id is:{}", goodsId);
        GoodsVO goodsVO = convertGoodsDTO2GoodsVO(goodsDTO);
        return ActionResult.getSuccessResult(goodsVO);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("商品搜索")
    public ActionResult<List<GoodsVO>> searchGoods(@NotBlank(message = "搜索词不能为空") String inputValue) {

        String input = inputValue.trim();

        //添加到热搜list
        redisService.increaseHotSearchList(input);
        //查询
        List<GoodsDTO> goodsDTOS = goodsService.searchGoods(input);
        List<GoodsVO> goods = new ArrayList<>();

        goodsDTOS.forEach(goodsDTO -> {
            GoodsVO goodsVO = convertGoodsDTO2GoodsVO(goodsDTO);
            goods.add(goodsVO);
        });

        log.info("search goods success");
        return ActionResult.getSuccessResult(goods);
    }

    @GetMapping(value = "/search/top/{num}")
    @ApiOperation("获取前num个热搜词")
    public ActionResult<Set<String>> getTopSearch(@PathVariable("num") int top) {
        if (top <= 0) {
            throw new ParamException(ErrorType.PARAM_ERROR, "top 必须大于0");
        }
        Set<String> topList = redisService.getHotSearchTopList(top);
        log.info("get top search list success");

        return ActionResult.getSuccessResult(topList);
    }

    /**
     * 得到商品list page vo对象
     *
     * @param goodsList 商品page
     * @return vo
     */
    private PageResult<List<GoodsListVO>> getGoodsListPageVO(PageResult<List<GoodsListDTO>> goodsList) {
        List<GoodsListVO> goodsListVOS = new ArrayList<>();

        goodsList.getData().forEach(goodsListDTO -> {
            GoodsListVO goodsListVO = convertGoodsListDTO2VO(goodsListDTO);
            goodsListVOS.add(goodsListVO);
        });
        PageResult<List<GoodsListVO>> pageResult = new PageResult<>();
        BeanUtils.copyProperties(goodsList, pageResult);
        pageResult.setData(goodsListVOS);
        return pageResult;
    }

    /**
     * 将GoodsListDTO转为GoodsListVO
     *
     * @param goodsListDTO dto
     * @return vo
     */
    private GoodsListVO convertGoodsListDTO2VO(GoodsListDTO goodsListDTO) {
        GoodsListVO goodsListVO = new GoodsListVO();
        BeanUtils.copyProperties(goodsListDTO, goodsListVO);

        List<GoodsVO> goodsVOS = new ArrayList<>();
        goodsListDTO.getGoods().forEach(goodsDTO -> {
            goodsVOS.add(convertGoodsDTO2GoodsVO(goodsDTO));
        });

        goodsListVO.setGoods(goodsVOS);
        return goodsListVO;
    }

    /**
     * 将GoodsDTO转换为GoodsVO
     *
     * @param goodsDTO dto
     * @return vo
     */
    private GoodsVO convertGoodsDTO2GoodsVO(GoodsDTO goodsDTO) {
        GoodsVO goodsVO = new GoodsVO();
        BeanUtils.copyProperties(goodsDTO, goodsVO);

        List<GoodsPictureVO> pictureVOS = new ArrayList<>();
        List<GoodsOptionVO> goodsOptionVOS = new ArrayList<>();

        goodsDTO.getPictures().forEach(pictureDTO -> {
            GoodsPictureVO pictureVO = new GoodsPictureVO();
            BeanUtils.copyProperties(pictureDTO, pictureVO);
            pictureVOS.add(pictureVO);
        });

        goodsDTO.getGoodsOptions().forEach(goodsOptionDTO -> {
            GoodsOptionVO goodsOptionVO = convertGoodsOptionDTO2VO(goodsOptionDTO);
            goodsOptionVOS.add(goodsOptionVO);
        });

        goodsVO.setPictures(pictureVOS);
        goodsVO.setGoodsOptions(goodsOptionVOS);

        return goodsVO;
    }

    /**
     * 将GoodsOptionDTO转化为GoodsOptionVO
     *
     * @param goodsOptionDTO dto
     * @return vo
     */
    private GoodsOptionVO convertGoodsOptionDTO2VO(GoodsOptionDTO goodsOptionDTO) {
        GoodsOptionVO goodsOptionVO = new GoodsOptionVO();
        BeanUtils.copyProperties(goodsOptionDTO, goodsOptionVO);
        List<GoodsOptionItemVO> goodsOptionItemVOS = new ArrayList<>();

        goodsOptionDTO.getGoodsOptionItems().forEach(goodsOptionItemDTO -> {
            GoodsOptionItemVO goodsOptionItemVO = new GoodsOptionItemVO();
            BeanUtils.copyProperties(goodsOptionItemDTO, goodsOptionItemVO);
            goodsOptionItemVOS.add(goodsOptionItemVO);
        });

        goodsOptionVO.setGoodsOptionItems(goodsOptionItemVOS);
        return goodsOptionVO;
    }

}
