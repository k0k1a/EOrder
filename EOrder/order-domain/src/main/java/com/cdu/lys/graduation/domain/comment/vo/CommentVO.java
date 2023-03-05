package com.cdu.lys.graduation.domain.comment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/30 15:24
 */
@Data
public class CommentVO {

    private Integer id;

    private String avatarUrl;

    @JsonProperty("nickName")
    private String nickname;

    private Integer serviceScore;

    private Integer environmentScore;

    private Integer tasteScore;

    private String content;

    private String reply;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private List<CommentPictureVO> picUrls;
}
