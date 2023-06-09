package com.wujiejie.qushuge.mapper;

import com.wujiejie.qushuge.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 27485
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2023-04-14 20:22:15
* @Entity com.wujiejie.qushuge.entity.SysRole
*/
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}




