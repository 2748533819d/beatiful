package com.wujiejie.qushuge.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图书表
 * @TableName sys_books
 */
@TableName(value ="sys_books")
@Data
public class Books implements Serializable {
    /**
     * 图书id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书作者
     */
    private String bookAuthor;

    /**
     * 图书类别
     */
    private String bookType;

    /**
     * 图书位置
     */
    private String bookLocation;

    /**
     * 图书状态
     */
    private String bookStatus;

    /**
     * 图书描述
     */
    private String bookDescription;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * ISBN码
     */
    private String isbnCode;

    /**
     * 是否被删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}