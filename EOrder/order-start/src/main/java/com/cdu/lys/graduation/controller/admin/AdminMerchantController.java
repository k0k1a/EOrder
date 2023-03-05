package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.merchant.bo.MerchantBO;
import com.cdu.lys.graduation.admin.merchant.bo.MerchantRealPictureBO;
import com.cdu.lys.graduation.admin.merchant.convertor.MerchantConvertor;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.domain.admin.vo.AdminMerchantVO;
import com.cdu.lys.graduation.repository.entity.MerchantDO;
import com.cdu.lys.graduation.repository.entity.MerchantRealPictureDO;
import com.cdu.lys.graduation.types.admin.merchant.MerchantQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/14 21:36
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统商家设置接口")
@Slf4j
public class AdminMerchantController {

    @Autowired
    private MerchantBO merchantBO;

    @Autowired
    private MerchantRealPictureBO merchantRealPictureBO;

    @Autowired
    private MerchantConvertor convertor;

    @ApiOperation("获取商户信息")
    @GetMapping(value = "/merchant/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<AdminMerchantVO> getMerchantInfo() {
        MerchantDO merchantDO = merchantBO.get();
        AdminMerchantVO adminMerchantVO = new AdminMerchantVO();

        List<MerchantRealPictureDO> merchantRealPictureDOS = merchantRealPictureBO.get();
        BeanUtils.copyProperties(merchantDO, adminMerchantVO);
        adminMerchantVO.setRealPictureUrl(convertor.convert2MerchantRealPictureVO(merchantRealPictureDOS));

        log.info("Admin get merchant info success");
        return ActionResult.getSuccessResult(adminMerchantVO);
    }

    @ApiOperation("修改商户信息")
    @PostMapping(value = "/merchant/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> modifyMerchant(@RequestBody @Valid MerchantQuery query) {
        MerchantDO merchantDO = new MerchantDO();
        BeanUtils.copyProperties(query, merchantDO);
        int i = merchantBO.update(merchantDO);
        merchantRealPictureBO.update(convertor.convert2PictureDO(query.getRealPictures()));

        if (i > 0) {
            log.info("Admin update merchant info success");
            return ActionResult.getSuccessResult("更新成功");
        }
        log.warn("Admin update merchant info fail");
        return ActionResult.getSuccessResult("更新失败");
    }

}
