package com.model.service;

import com.model.entity.User;
import java.util.List;

public interface UserService {
    /** 用户注册 */
    boolean register(User user);
    /** 用户登录 */
    User login(String username, String password);
    /** 根据ID查询 */
    User findById(int id);
    /** 更新个人信息 */
    boolean updateProfile(User user);
    /** 查询所有用户（管理员） */
    List<User> findAll();
}
