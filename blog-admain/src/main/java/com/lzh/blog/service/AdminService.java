package com.lzh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzh.blog.mapper.AdminMapper;
import com.lzh.blog.mapper.PermissionMapper;
import com.lzh.blog.pojo.Admin;
import com.lzh.blog.pojo.Permission;
import com.lzh.blog.mapper.AdminMapper;
import com.lzh.blog.mapper.PermissionMapper;
import com.lzh.blog.pojo.Admin;
import com.lzh.blog.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public Admin findAdminByUserName(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username).last("limit 1");
        Admin adminUser = adminMapper.selectOne(queryWrapper);
        return adminUser;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId){
        return permissionMapper.findPermissionsByAdminId(adminId);
    }

}
