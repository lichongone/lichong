package com.example.one.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
 * @since 2021-08-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  implements  Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String password;

    private String account;

    private String phone;

    private String createBy;

    private LocalDateTime createTime;

    private List<Role> roles;

    private List<Permission> permissions;




}
