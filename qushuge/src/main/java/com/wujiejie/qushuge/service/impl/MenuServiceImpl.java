package com.wujiejie.qushuge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiejie.qushuge.entity.Menu;
import com.wujiejie.qushuge.service.MenuService;
import com.wujiejie.qushuge.mapper.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 27485
 * @description 针对表【sys_menu】的数据库操作Service实现
 * @createDate 2023-04-14 20:56:59
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        List<Menu> list = list(queryWrapper);
        //找出一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            //筛选所有数据中pid=父级id数据的二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPId())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}




