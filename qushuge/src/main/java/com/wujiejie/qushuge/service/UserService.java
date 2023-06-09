package com.wujiejie.qushuge.service;

import com.wujiejie.qushuge.controller.dto.UserDTO;
import com.wujiejie.qushuge.controller.dto.UserPasswordDTO;
import com.wujiejie.qushuge.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wujiejie
 * @since 2023-04-14
 */
public interface UserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
