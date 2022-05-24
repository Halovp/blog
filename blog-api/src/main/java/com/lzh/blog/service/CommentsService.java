package com.lzh.blog.service;

import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.params.CommentParam;

public interface CommentsService {

    /**
     * 根据文章id查询所有的评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
