package com.lzh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzh.blog.dao.mapper.ArticleMapper;
import com.lzh.blog.dao.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    /**
     * 期望此操作在线程池执行，不会影响主线程
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper,Article article){

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,queryWrapper);
        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
            //System.out.println(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
