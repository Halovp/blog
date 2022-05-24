package com.lzh.blog.controller;

import com.lzh.blog.service.TagService;
import com.lzh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 返回最热标签
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 3;
        //List<Tag> tagVoList = tagService.hots(limit);

        return tagService.hots(limit);
    }
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }

}