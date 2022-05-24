package com.lzh.blog.service.impl;

import com.lzh.blog.dao.mapper.TagMapper;
import com.lzh.blog.dao.pojo.Tag;
import com.lzh.blog.service.TagService;
import com.lzh.blog.vo.Result;
import com.lzh.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;


    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //tagMapper.
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1.标签所拥有的文章数量最多 最热标签
         * 2.查询 根据tag_id 分组 从大到小 排列 取前六位
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList=tagMapper.findTagsByTagIds(tagIds);
        //int i=1/0;
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(copyList(tags));
    }

    /**
     * 查询并返回所有标签
     * @return
     */
    @Override
    public Result findAllDetail() {

        List<Tag> tags = this.tagMapper.selectList(null);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);

        return Result.success(copy(tag));
    }
}
