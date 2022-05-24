package com.lzh.blog.vo.params;

import com.lzh.blog.dao.pojo.ArticleBodyParam;
import com.lzh.blog.vo.CategoryVo;
import com.lzh.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
