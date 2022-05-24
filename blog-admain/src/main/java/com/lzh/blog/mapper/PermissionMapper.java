package com.lzh.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.blog.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    List<Permission> findPermissionsByAdminId(Long adminId);

}
