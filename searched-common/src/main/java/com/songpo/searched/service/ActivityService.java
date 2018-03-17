package com.songpo.searched.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.mapper.SlActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 刘松坡
 */
@Service
public class ActivityService {

    @Autowired
    private SlActivityMapper mapper;

    /**
     * 添加活动信息
     *
     * @param activity 活动信息
     * @return
     */
    public int save(SlActivity activity) {
        return this.mapper.insertSelective(activity);
    }

    /**
     * 删除活动信息
     *
     * @param id 唯一标识符
     * @return
     */
    public int delete(String id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新活动信息
     *
     * @param activity 活动信息
     * @return
     */
    public int update(SlActivity activity) {
        return this.mapper.updateByPrimaryKeySelective(activity);
    }

    /**
     * 查询商品活动信息
     *
     * @param id 唯一标识符
     * @return
     */
    public SlActivity findById(String id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有商品活动信息
     *
     * @param name 活动名称
     * @return
     */
    public List<SlActivity> findAll(String name) {
        if (!StringUtils.isEmpty(name)) {
            return this.mapper.selectAll();
        } else {
            return this.mapper.select(new SlActivity() {{
                setName(name);
            }});
        }
    }

    /**
     * 分页查询
     *
     * @param name     活动名称
     * @param pageNum  页码
     * @param pageSize 容量
     * @return
     */
    public PageInfo<SlActivity> findByPage(String name, Integer pageNum, Integer pageSize) {
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);

        List<SlActivity> data;
        if (!StringUtils.isEmpty(name)) {
            data = this.mapper.selectAll();
        } else {
            data = this.mapper.select(new SlActivity() {{
                setName(name);
            }});
        }
        return new PageInfo<>(data);
    }
}
