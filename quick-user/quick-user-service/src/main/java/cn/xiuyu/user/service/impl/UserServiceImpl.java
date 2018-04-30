/*
 * $Id: UserServiceImpl.java, 2018年4月29日 下午8:17:16 XiuYu.Ge Exp $
 * 
 * Copyright (c) 2012 zzcode Technologies Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by zzcode or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package cn.xiuyu.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.xiuyu.user.model.GroupModel;
import cn.xiuyu.user.model.ResourceModel;
import cn.xiuyu.user.model.UserModel;
import cn.xiuyu.user.repository.UserRepository;
import cn.xiuyu.user.service.UserService;

/**
 * <p>
 * Title: UserServiceImpl
 * </p>
 * <p>
 * Description:用户服务具体实现
 * </p>
 * 
 * @author XiuYu.Ge
 * @created 2018年4月29日 下午8:17:16
 * @modified [who date description]
 * @check [who date description]
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void test() {
        System.out.println(userRepository);
        UserModel user = new UserModel();
        user.setUsername("test");
        user.setEnable(Byte.valueOf("1"));
        user.setPassword("123123");
        userRepository.save(user);
        System.out.println("保存成功");
    }

    /**
     * @see cn.xiuyu.core.service.base.BaseService#save(java.lang.Object)
     */
    @Override
    public UserModel save(UserModel model) {
        return userRepository.save(model);
    }

    /**
     * @see cn.xiuyu.core.service.base.BaseService#update(java.lang.Object)
     */
    @Override
    public UserModel update(UserModel model) {
        return userRepository.save(model);
    }

    /**
     * @see cn.xiuyu.core.service.base.BaseService#show(java.lang.Object)
     */
    @Override
    public UserModel show(Integer id) {
        Optional<UserModel> result = userRepository.findById(id);
        UserModel user = null;
        if (result.isPresent()) {
            user = result.get();
            user.setGroupSet(null);
        }
        return user;
    }

    /**
     * @see cn.xiuyu.core.service.base.BaseService#delete(java.lang.Object)
     */
    @Override
    public UserModel delete(Integer id) {
        UserModel user = show(id);
        if (user != null) {
            userRepository.deleteById(id);
        }
        return user;
    }

    /**
     * @see cn.xiuyu.user.service.UserService#findAll(java.lang.Integer)
     */
    @Override
    public UserModel findAll(Integer id) {
        Optional<UserModel> result = userRepository.findById(id);
        UserModel user = null;
        if (result.isPresent()) {
            user = result.get();
            user.setGroupList(new ArrayList<>(user.getGroupSet()));
            user.setGroupSet(new HashSet<>());

            List<ResourceModel> resourceList = new ArrayList<ResourceModel>();
            user.getGroupList().stream().forEach(group -> {
                group.setResourceList(new ArrayList<>(group.getResourceSet()));
                group.setResourceSet(new HashSet<>());

                resourceList.addAll(group.getResourceList());
            });

            user.setResourceList(resourceList);
        }
        return user;
    }

    /**
     * @see cn.xiuyu.user.service.UserService#findGroup(java.lang.Integer)
     */
    @Override
    public GroupModel findGroup(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see cn.xiuyu.user.service.UserService#findResource(java.lang.Integer)
     */
    @Override
    public ResourceModel findResource(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

}
