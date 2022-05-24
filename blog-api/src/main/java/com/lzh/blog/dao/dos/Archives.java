package com.lzh.blog.dao.dos;

import lombok.Data;

@Data //不需要持久化的数据对象
public class Archives {
    private Integer year;

    private Integer month;

    private Integer count;
}
