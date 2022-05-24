package com.lzh.blog.controller;

import com.lzh.blog.common.aop.LogAnnotation;
import com.lzh.blog.service.ArticleService;
import com.lzh.blog.vo.ArticleVo;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.ArticleParam;
import com.lzh.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController//代表我们返回的是一个json格式的数据
@RequestMapping("articles")
public class ArticleController {
    /**
     * 从请求体中接受参数，restful风格
     * 首页 文章列表
     * @param pageParams
     */
    @Autowired
    private ArticleService articleService;//暂时没有对应的实现类


    @PostMapping
    //加上此注解，代表对此接口记录日志
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);
    }
    @PostMapping("new")
    public Result newArticle(){
        int limit=6;
        return articleService.newArticles(limit);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        //int limit=6;
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

}
