package com.wujiejie.qushuge.mapper;

import com.wujiejie.qushuge.controller.dto.UserPasswordDTO;
import com.wujiejie.qushuge.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wujiejie
 * @since 2023-04-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update sys_user set password = #{newPassword} where username=#{username} and password=#{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);
}
