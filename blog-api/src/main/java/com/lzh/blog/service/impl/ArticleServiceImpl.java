package com.lzh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.blog.dao.dos.Archives;
import com.lzh.blog.dao.mapper.ArticleBodyMapper;
import com.lzh.blog.dao.mapper.ArticleMapper;
import com.lzh.blog.dao.mapper.ArticleTagMapper;
import com.lzh.blog.dao.mapper.CategoryMapper;
import com.lzh.blog.dao.pojo.*;
import com.lzh.blog.service.*;
import com.lzh.blog.utils.UserThreadLocal;
import com.lzh.blog.vo.ArticleBodyVo;
import com.lzh.blog.vo.ArticleVo;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.TagVo;
import com.lzh.blog.vo.params.ArticleParam;
import com.lzh.blog.vo.params.PageParams;
import javafx.scene.control.TextArea;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;


    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPagesize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }

//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 分页查询数据库表，page对象
//         */
//        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPagesize());
//
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();//排序控制器
//        //条件构造器，order by creatdate，按照权重置顶排序
//        //查询文章的参数 加上分类id，判断不为空 加上分类条件
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null){
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0){
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//        }
//        //articleMapper.listArchives();
//        if(pageParams.getMonth() != null){
//            queryWrapper.eq(Article::getCreateDate,)
//        }
//
//        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();//得到列表数据
//
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//
//        return Result.success(articleVoList);
//        //return null;
//    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(Article::getViewCounts);
        //queryWrapper.orderByDesc();
        //queryWrapper.select();
//        queryWrapper.select((Article s)-> s.getId());


        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        //selet id,title from article order by viewcounts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        //selet id,title from article order by viewcounts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        /**
         * 文章归档
         */
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo copy = copy(article,true,true,true,true);
        /**
         * 看完文章了，新增阅读数，本应该直接返回数据，但似乎更新操作更新时加了写锁，阻塞其他操作
         * 性能低
         * 更新增加了耗时，一旦更新操作出现问题，不能影响查看文章的操作
         * 可以把更新操作丢到线程池中，就和主线程不相关,为啥使用的是类，不建立一个接口，好像是额外增加了一个服务
         * 使任务异步执行
         */
        threadService.updateViewCount(articleMapper,article);

        return Result.success(copy);
    }

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        /**
         * articlebody部分
         */
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();



        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);
        articleMapper.insert(article);

        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        /**
         * articlebody部分
         */
        ArticleBody articleBody = new ArticleBody();
        //我觉得这样可以啊
//        BeanUtils.copyProperties(articleParam.getBody(),articleBody);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());

        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        /**
         * 这里有因为没有update，使得文章加载失败，还没深究是什么问题
         * 应为开启了事务
         */
        articleMapper.updateById(article);
        HashMap<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList =new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }

        return articleVoList;
    }


    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){

        ArticleVo articleVo = new ArticleVo();
        System.out.println(article);
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不四所有接口都需要标签，作者信息
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyId = article.getBodyId();

            //String content = articleBodyMapper.selectById(bodyId).getContent();
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            articleBodyVo.setContent(articleBodyMapper.selectById(bodyId).getContent());
            articleVo.setBody(articleBodyVo);
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }

}
