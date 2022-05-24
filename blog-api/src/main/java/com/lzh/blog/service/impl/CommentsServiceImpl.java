package com.lzh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzh.blog.dao.mapper.CommentsMapper;
import com.lzh.blog.dao.pojo.Comment;
import com.lzh.blog.dao.pojo.SysUser;
import com.lzh.blog.service.CommentsService;
import com.lzh.blog.service.SysUserService;
import com.lzh.blog.utils.UserThreadLocal;
import com.lzh.blog.vo.CommentVo;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.UserVo;
import com.lzh.blog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long articleId) {
        /**
         * 分析步骤，
         * 1.根据文章id查询平评论列表
         * 2.根据作者的id查询作者的信息
         * 3。判断是否有子评论
         * 4，如果有就根据评论id查询
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);//判断是否有子评论
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));

    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setAuthor(sysUserService.findUserVoById(comment.getAuthorId()));
        Integer level = comment.getLevel();
        if(1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        if(level  > 1){
            Long toUid = comment.getToUid();
            UserVo userById = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userById);
        }
        return commentVo;

    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        ArrayList<CommentVo> commentVos = new ArrayList<>();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);

        return copyList(commentMapper.selectList(queryWrapper));
    }
}
