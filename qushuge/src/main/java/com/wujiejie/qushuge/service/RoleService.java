package com.wujiejie.qushuge.service;

import com.wujiejie.qushuge.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 27485
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2023-04-14 20:22:15
*/
public interface RoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);

}

