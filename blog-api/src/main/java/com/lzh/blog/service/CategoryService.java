package com.lzh.blog.service;

import com.lzh.blog.vo.CategoryVo;
import com.lzh.blog.vo.Result;


public interface CategoryService {

    CategoryVo findCategoryById(Long id);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}