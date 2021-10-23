package com.example.one.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class Recode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String content;


}
