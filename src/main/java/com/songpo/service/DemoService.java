package com.songpo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.entity.Demo;
import com.songpo.mapper.DemoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class DemoService {

    @Autowired
    private DemoMapper demoMapper;

    /**
     * 保存
     *
     * @param title   标题
     * @param content 内容
     */
    public void save(String title, String content) {
        Demo demo = new Demo();
        demo.setId(UUID.randomUUID().toString());
        demo.setTitle(title);
        demo.setContent(content);
        demo.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.demoMapper.insertSelective(demo);
    }

    /**
     * 删除
     *
     * @param id 唯一标识
     */
    public void delete(String id) {
        this.demoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param id      唯一标识
     * @param title   标题
     * @param content 内容
     */
    public void update(String id, String title, String content) {
        Demo demo = this.demoMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(title)) {
            demo.setTitle(title);
        }

        if (StringUtils.isNotBlank(content)) {
            demo.setContent(content);
        }
        this.demoMapper.updateByPrimaryKeySelective(demo);
    }

    /**
     * 查询
     *
     * @param id 唯一标识
     * @return 信息
     */
    public Demo findOne(String id) {
        return this.demoMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     *
     * @param title 标题
     * @param page  页码，从1开始
     * @param size  数据量，默认10
     * @param sort  排序规则
     * @return 分页信息
     */
    public PageInfo<Demo> findPage(String title, Integer page, Integer size, String sort) {
        if (null == page || page <= 0) {
            page = 1;
        }

        if (null == size || size <= 0) {
            size = 10;
        }

        Example example = new Example(Demo.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(title)) {
            criteria.andLike("title", "%" + title + "%");
        }

        if (StringUtils.isNotBlank(sort)) {
            example.setOrderByClause(sort);
        }

        PageHelper.startPage(page, size);
        List<Demo> list = this.demoMapper.selectByExample(example);

        return new PageInfo<>(list);
    }
}
