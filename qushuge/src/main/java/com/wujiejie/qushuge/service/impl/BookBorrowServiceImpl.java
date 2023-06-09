package com.wujiejie.qushuge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiejie.qushuge.entity.BookBorrow;
import com.wujiejie.qushuge.service.BookBorrowService;
import com.wujiejie.qushuge.mapper.BookBorrowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 27485
* @description 针对表【sys_book_borrow】的数据库操作Service实现
* @createDate 2023-04-16 14:47:25
*/
@Service
public class BookBorrowServiceImpl extends ServiceImpl<BookBorrowMapper, BookBorrow>
    implements BookBorrowService {
}




