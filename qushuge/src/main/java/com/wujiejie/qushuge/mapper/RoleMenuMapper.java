package com.wujiejie.qushuge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wujiejie.qushuge.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectBYRoleId(@Param("roleId") Integer roleId);
}
