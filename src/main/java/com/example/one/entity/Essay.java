package com.example.one.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <p>
 * 
 * </p>
 *
 * @author LC
 * @since 2021-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Essay implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    @JsonProperty("art_id")
    private Integer artId;

    private String title;
    @JsonProperty("aut_id")
    private Integer autId;
    @JsonProperty("aut_name")
    private String autName;
    @JsonProperty("comm_count")
    private Integer commCount;

    private LocalDateTime pubdate;

    /**
     * 封面
     */
    private String cover;

    /**
     * 点赞数
     */
    @JsonProperty("like_count")
    private Integer likeCount;
    @JsonProperty("collect_count")
    private Integer collectCount;


}
