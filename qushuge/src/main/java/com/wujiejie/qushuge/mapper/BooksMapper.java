package com.wujiejie.qushuge.mapper;

import com.wujiejie.qushuge.entity.Books;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 27485
* @description 针对表【sys_books(图书表)】的数据库操作Mapper
* @createDate 2023-04-16 14:45:37
* @Entity com.wujiejie.qushuge.entity.SysBooks
*/
public interface BooksMapper extends BaseMapper<Books> {
    List<Books> findBorrowPage(Integer pageBeginNum, Integer pageSize, String bookName, String isbnCode, Integer userId);
}




