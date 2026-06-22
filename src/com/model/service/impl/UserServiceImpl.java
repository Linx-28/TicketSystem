package com.model.service.impl;

import com.model.dao.UserDao;
import com.model.entity.User;
import com.model.service.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User exist = userDao.findByUsername(user.getUsername());
        if (exist != null) {
            return false;
        }
        user.setRole("user");
        int result = userDao.insert(user);
        return result > 0;
    }

    @Override
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public boolean updateProfile(User user) {
        return userDao.update(user) > 0;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}