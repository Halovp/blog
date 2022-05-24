package com.lzh.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {

    @TableId(type = IdType.AUTO)//id自增策略
    private Long id;

    private String name;

    private String path;

    private String description;
}
