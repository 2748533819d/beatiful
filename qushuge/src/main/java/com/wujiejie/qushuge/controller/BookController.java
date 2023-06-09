package com.wujiejie.qushuge.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujiejie.qushuge.common.Constants;
import com.wujiejie.qushuge.common.Result;
import com.wujiejie.qushuge.entity.BookBorrow;
import com.wujiejie.qushuge.entity.Books;
import com.wujiejie.qushuge.entity.User;
import com.wujiejie.qushuge.exception.ServiceException;
import com.wujiejie.qushuge.mapper.BooksMapper;
import com.wujiejie.qushuge.service.BookBorrowService;
import com.wujiejie.qushuge.service.BooksService;
import com.wujiejie.qushuge.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BooksService booksService;

    @Resource
    BookBorrowService bookBorrowService;

    @Resource
    BooksMapper booksMapper;

    @Resource
    UserService userService;

    @PostMapping("borrow_book")
    @Transactional
    public Result borrowBookByUserIdAndBookId(@RequestBody BookBorrow bookBorrow) {
        // 图书id
        Integer bookId = bookBorrow.getBookId();
        // 用户id
        Integer userId = bookBorrow.getUserId();
        // 借阅时间
        Date borrowDate = bookBorrow.getBorrowDate();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return Result.error(Constants.CODE_400, "借阅图书失败");
        }
        if("ROLE_TEACHER".equals(user.getRole()))
        {
            LambdaQueryWrapper<Books> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Books::getId, bookId);
            Books book = booksService.getOne(queryWrapper1);
            if ((book == null) || (book.getBookStatus().equals(Constants.BOOKDISABLE))) {
                throw new ServiceException(Constants.CODE_400, "图书不存在或被借出");
            }
            BookBorrow booksBorrow1 = new BookBorrow();
            booksBorrow1.setBookId(bookId);
            booksBorrow1.setUserId(userId);
            booksBorrow1.setBorrowState(0);
            booksBorrow1.setBorrowDate(borrowDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.MONTH, 3);
            Date closeDate = calendar.getTime();
            booksBorrow1.setCloseDate(closeDate);
            boolean flag = bookBorrowService.save(booksBorrow1);
            if (!flag) {
                return Result.error(Constants.CODE_600, "借阅图书失败");
            }
            book.setBookStatus(Constants.BOOKDISABLE);
            boolean update = booksService.update(book, queryWrapper1);
            if (!update) {
                return Result.error(Constants.CODE_600, "借阅图书失败");
            }
            return Result.success("借阅图书成功");
        } else if ("ROLE_STUDENT".equals(user.getRole())) {
            LambdaQueryWrapper<Books> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Books::getId, bookId);
            Books book = booksService.getOne(queryWrapper1);
            if ((book == null) || (book.getBookStatus().equals(Constants.BOOKDISABLE))) {
                throw new ServiceException(Constants.CODE_400, "图书不存在或被借出");
            }
            BookBorrow booksBorrow1 = new BookBorrow();
            booksBorrow1.setBookId(bookId);
            booksBorrow1.setUserId(userId);
            booksBorrow1.setBorrowState(0);
            booksBorrow1.setBorrowDate(borrowDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.DAY_OF_WEEK, 4);
            Date closeDate = calendar.getTime();
            booksBorrow1.setCloseDate(closeDate);
            boolean flag = bookBorrowService.save(booksBorrow1);
            if (!flag) {
                return Result.error(Constants.CODE_600, "借阅图书失败");
            }
            book.setBookStatus(Constants.BOOKDISABLE);
            boolean update = booksService.update(book, queryWrapper1);
            if (!update) {
                return Result.error(Constants.CODE_600, "借阅图书失败");
            }
            return Result.success("借阅图书成功");
        } else {
            return Result.error(Constants.CODE_600,"没有结束权限");
        }
    }

    /**
     * 查询图书
     *
     * @param pageNum  当且页面
     * @param pageSize 页面大小
     * @param bookName 图书名称
     * @param isbnCode ISBN码
     * @return 图书列表
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String bookName,
                           @RequestParam(defaultValue = "") String isbnCode) {
        IPage<Books> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Books> queryWrapper = new QueryWrapper<>();
        if (!"".equals(bookName)) {
            queryWrapper.like("book_name", bookName);
        }
        if (!"".equals(isbnCode)) {
            queryWrapper.like("isbn_code", isbnCode);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(booksService.page(page, queryWrapper));
    }


    /**
     * 增加或修改图书
     *
     * @param book 图书
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Books book) {
        return Result.success(booksService.saveOrUpdate(book));
    }

    /**
     * 根据图书id删除图书
     *
     * @param id 图书id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(booksService.removeById(id));
    }

    /**
     * 根据id批量删除
     *
     * @param ids id的列表
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteBath(@RequestBody List<Integer> ids) {
        return Result.success(booksService.removeByIds(ids));
    }


    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Books> list = booksService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("bookName", "图书名称");
        writer.addHeaderAlias("bookAuthor", "图书作者");
        writer.addHeaderAlias("bookType", "图书类型");
        writer.addHeaderAlias("bookLocation", "图书位置");
        writer.addHeaderAlias("bookStatus", "是否被借出");
        writer.addHeaderAlias("bookDescription", "图书描述");
        writer.addHeaderAlias("isbnCode", "ISBN码");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "更新时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("图书信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Books> books = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Books book = new Books();
            book.setBookName(row.get(1).toString());
            book.setBookAuthor(row.get(2).toString());
            book.setBookType(row.get(3).toString());
            book.setBookLocation(row.get(4).toString());
            book.setBookStatus(row.get(5).toString());
            book.setBookDescription(row.get(6).toString());
            book.setIsbnCode(row.get(7).toString());
            books.add(book);
        }

        booksService.saveBatch(books);
        return Result.success(true);
    }

    /**
     * 归还图书
     *
     * @param bookBorrow
     * @return
     */
    @PostMapping("return_book")
    @Transactional
    public Result returnBookByUserIdAndBookId(@RequestBody BookBorrow bookBorrow) {
        Integer userId = bookBorrow.getUserId();
        Integer bookId = bookBorrow.getBookId();
        Date retrunDate = bookBorrow.getReturnDate();
        QueryWrapper<BookBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("borrow_state", 0);
        BookBorrow one = bookBorrowService.getOne(queryWrapper);
        if (one != null) {
            one.setReturnDate(retrunDate);
            one.setBorrowState(1);
            bookBorrowService.saveOrUpdate(one);
            QueryWrapper<Books> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", bookId);
            Books one1 = booksService.getOne(queryWrapper1);
            one1.setBookStatus(Constants.BOOKAVAILABLE);
            booksService.saveOrUpdate(one1);
            return Result.success();
        }
        return Result.error(Constants.CODE_400, "没有该图书");
    }

    /**
     * 查询图书
     *
     * @param pageNum  当且页面
     * @param pageSize 页面大小
     * @param bookName 图书名称
     * @param isbnCode ISBN码
     * @return 图书列表
     */
    @GetMapping("/borrow/page")
    public Result findBorrowPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String bookName,
                                 @RequestParam(defaultValue = "") String isbnCode,
                                 @RequestParam Integer userId) {
        Integer pageBeginNum = (pageNum-1)*pageSize;
        if(StrUtil.isNotBlank(bookName)) {
            bookName = "%"+bookName+"%";
        }
        if(StrUtil.isNotBlank(isbnCode)) {
            isbnCode = "%"+isbnCode+"%";
        }
        return Result.success(booksMapper.findBorrowPage(pageBeginNum, pageSize, bookName, isbnCode, userId));
    }
}
