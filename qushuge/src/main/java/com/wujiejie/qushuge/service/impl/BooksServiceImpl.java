package com.wujiejie.qushuge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiejie.qushuge.entity.Books;
import com.wujiejie.qushuge.service.BooksService;
import com.wujiejie.qushuge.mapper.BooksMapper;
import org.springframework.stereotype.Service;

/**
* @author 27485
* @description 针对表【sys_books(图书表)】的数据库操作Service实现
* @createDate 2023-04-16 14:45:38
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService {

}




