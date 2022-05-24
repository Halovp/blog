package com.lzh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.lzh.blog.dao.mapper.CategoryMapper;
import com.lzh.blog.dao.pojo.Category;
import com.lzh.blog.service.CategoryService;
import com.lzh.blog.vo.CategoryVo;
import com.lzh.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long id){
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        List<Category> categories = categoryMapper.selectList(null);
        ArrayList<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVos.add(categoryVo);
        }
        return Result.success(categoryVos);
    }

    @Override
    public Result findAllDetail() {
        List<Category> categories = categoryMapper.selectList(null);

        ArrayList<CategoryVo>  categoryVos= new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVos.add(categoryVo);
        }
        return Result.success(categoryVos);
    }

    @Override
    public Result categoriesDetailById(Long id) {

//        Scanner scanner = new Scanner(System.in, UTF8Writer);


        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = copy(category);
        return Result.success(categoryVo);
    }

    private CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}