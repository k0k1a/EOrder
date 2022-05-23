package com.cdu.lys.graduation.domain.goods.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/21 13:49
 */
@Data
public class GoodsListVO {

    private String type;

    private List<GoodsVO> goods;
}
