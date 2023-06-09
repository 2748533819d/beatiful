package com.wujiejie.qushuge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName sys_book_borrow
 */
@TableName(value ="sys_book_borrow")
@Data
public class BookBorrow implements Serializable {
    /**
     * 借阅表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 图书编号
     */
    private Integer bookId;

    /**
     * 借阅日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date borrowDate;

    /**
     * 归还日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date returnDate;
    /**
     * 应该归还时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date closeDate;

    /**
     * 借阅状态
     */
    private Integer borrowState;

    /**
     * 创建日期
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}