package com.wujiejie.qushuge.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiejie.qushuge.entity.Menu;
import com.wujiejie.qushuge.entity.Role;
import com.wujiejie.qushuge.entity.RoleMenu;
import com.wujiejie.qushuge.mapper.RoleMenuMapper;
import com.wujiejie.qushuge.service.MenuService;
import com.wujiejie.qushuge.service.RoleService;
import com.wujiejie.qushuge.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 27485
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2023-04-14 20:22:15
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;


    @Resource
    private MenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        //先删除当前角色id所有的绑定关系
        roleMenuMapper.delete(queryWrapper);
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        //再把前端传过来的菜单id数组绑定到当前的角色id上去
        for(Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if(menu.getPId() != null && !menuIdsCopy.contains(menu.getPId())){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPId());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPId());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectBYRoleId(roleId);
    }
}




