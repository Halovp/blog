package com.lzh.blog.dao.pojo;

import lombok.Data;

@Data//文章
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
