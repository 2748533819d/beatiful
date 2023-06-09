package com.wujiejie.qushuge.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName sys_role
 */
@TableName(value = "sys_role")
@Data
public class Role implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否被删除
     */
    @TableLogic
    private Integer isDeleted;

    private LocalDateTime createTime;
    /**
     * 角色唯一标识
     */
    private String flag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}