package com.wujiejie.qushuge.controller.dto;

import com.wujiejie.qushuge.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接收前端登录请求的参数
 */
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String token;
    private String role;
    private Integer status;
    private List<Menu> menus;
}
