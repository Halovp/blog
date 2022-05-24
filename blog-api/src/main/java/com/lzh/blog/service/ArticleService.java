package com.lzh.blog.service;

import com.lzh.blog.vo.ArticleVo;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.ArticleParam;
import com.lzh.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticles(int limit);

    Result listArchives();

    /**
     * 查询文章
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
