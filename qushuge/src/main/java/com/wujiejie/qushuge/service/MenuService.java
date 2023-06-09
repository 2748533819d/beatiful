package com.wujiejie.qushuge.service;

import com.wujiejie.qushuge.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 27485
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2023-04-14 20:56:59
*/
public interface MenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
