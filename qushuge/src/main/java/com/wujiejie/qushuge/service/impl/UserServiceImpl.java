package com.wujiejie.qushuge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiejie.qushuge.common.Constants;
import com.wujiejie.qushuge.controller.dto.UserDTO;
import com.wujiejie.qushuge.controller.dto.UserPasswordDTO;
import com.wujiejie.qushuge.entity.Menu;
import com.wujiejie.qushuge.entity.User;
import com.wujiejie.qushuge.exception.ServiceException;
import com.wujiejie.qushuge.mapper.RoleMapper;
import com.wujiejie.qushuge.mapper.RoleMenuMapper;
import com.wujiejie.qushuge.mapper.UserMapper;
import com.wujiejie.qushuge.service.MenuService;
import com.wujiejie.qushuge.service.UserService;
import com.wujiejie.qushuge.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wujiejie
 * @since 2023-04-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String SALT = "wujiejie";

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        userDTO.setPassword(encryptPassword);
        User one = getUserInfo(userDTO);
        if (one != null) {
            if(one.getStatus() == 1)
            {
                throw new ServiceException(Constants.CODE_600, "用户已被禁用");
            }
            BeanUtil.copyProperties(one, userDTO, true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            String role = one.getRole();
            //当前角色的所有菜单id集合
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (username.length() < 4) {
            throw new ServiceException(Constants.CODE_400, "用户账号太短");
        }
        if (password.length() < 8) {
            throw new ServiceException(Constants.CODE_400, "设置密码太短");
        }
        //账户不能有特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            throw new ServiceException(Constants.CODE_400, "账户不能有特殊字符");
        }
        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        userDTO.setPassword(encryptPassword);
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名已存在");
        }
        return one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        if(userPasswordDTO.getPassword().length()<8)
        {
            throw new ServiceException(Constants.CODE_400, "设置密码太短");
        }
        else{
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPasswordDTO.getPassword()).getBytes());
            String encryptNewPassword = DigestUtils.md5DigestAsHex((SALT + userPasswordDTO.getNewPassword()).getBytes());
            userPasswordDTO.setPassword(encryptPassword);
            userPasswordDTO.setNewPassword(encryptNewPassword);
            int update = userMapper.updatePassword(userPasswordDTO);
            if(update < 1)
            {
                throw new ServiceException(Constants.CODE_600,"密码错误");
            }
        }

    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        List<Integer> menuIds = roleMenuMapper.selectBYRoleId(roleId);
        //查出系统所有菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
