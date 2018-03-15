package com.songpo.searched.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author SongpoLiu
 */
public class BaseService<T, PK> {

    private Mapper<T> mapper;

    public BaseService(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    /**
     * 添加
     *
     * @param t 实体
     * @return 添加结果 1：添加成功 2：添加失败
     */
    public int insert(T t) {
        return this.mapper.insert(t);
    }

    /**
     * 添加
     *
     * @param t 实体
     * @return 添加结果 1：添加成功 2：添加失败
     */
    public int insertSelective(T t) {
        return this.mapper.insertSelective(t);
    }

    /**
     * 删除
     *
     * @param t 实体
     * @return 删除结果 1：删除成功 2：删除失败
     */
    public int delete(T t) {
        return this.mapper.delete(t);
    }

    /**
     * 根据唯一标识删除
     *
     * @param id 删除的实体
     * @return 删除结果 1：删除成功 2：删除失败
     */
    public int deleteByPrimaryKey(PK id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 条件删除
     *
     * @param e 条件
     * @return 删除结果 1：删除成功 2：删除失败
     */
    public int deleteByExample(Example e) {
        return this.mapper.deleteByExample(e);
    }

    /**
     * 根据条件更新
     *
     * @param t 更新的实体
     * @param e 条件
     * @return 更新结果 1：更新成功 2：更新失败
     */
    public int updateByExample(T t, Example e) {
        return this.mapper.updateByExample(t, e);
    }

    /**
     * 根据条件更新
     *
     * @param t 更新的实体
     * @param e 条件
     * @return 更新结果 1：更新成功 2：更新失败
     */
    public int updateByExampleSelective(T t, Example e) {
        return this.mapper.updateByExampleSelective(t, e);
    }

    /**
     * 根据唯一标识更新
     *
     * @param t 更新的实体
     * @return 更新结果 1：更新成功 2：更新失败
     */
    public int updateByPrimaryKey(T t) {
        return this.mapper.updateByPrimaryKey(t);
    }

    /**
     * 根据唯一标识更新
     *
     * @param t 更新的实体
     * @return 更新结果 1：更新成功 2：更新失败
     */
    public int updateByPrimaryKeySelective(T t) {
        return this.mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据条件查询是否存在
     *
     * @param t 查询条件
     * @return 是否存在
     */
    public boolean exist(T t) {
        return this.mapper.selectCount(t) > 0;
    }

    /**
     * 根据条件查询是否存在
     *
     * @param e 查询条件
     * @return 是否存在
     */
    public boolean exist(Example e) {
        return this.mapper.selectCountByExample(e) > 0;
    }

    /**
     * 条件查询
     *
     * @param t 条件
     * @return 符合条件的元素集合
     */
    public List<T> select(T t) {
        return this.mapper.select(t);
    }

    /**
     * 条件查询
     *
     * @param t 条件
     * @return 符合条件的元素集合
     */
    public T selectOne(T t) {
        return this.mapper.selectOne(t);
    }

    /**
     * 查询所有
     *
     * @return 符合条件的元素集合
     */
    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    /**
     * 根据主键查询
     *
     * @param id 唯一标识
     * @return 符合条件的元素
     */
    public T selectByPrimaryKey(PK id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 条件查询
     *
     * @param e 条件
     * @return 符合条件的元素集合
     */
    public List<T> selectByExample(Example e) {
        return this.mapper.selectByExample(e);
    }

    /**
     * 查询符合条件的记录数量
     *
     * @param t 查询条件
     * @return 记录数量
     */
    public int selectCount(T t) {
        return this.mapper.selectCount(t);
    }

    /**
     * 查询符合条件的记录数量
     *
     * @param e 条件
     * @return 记录数量
     */
    public int selectCountByExample(Example e) {
        return this.mapper.selectCountByExample(e);
    }

    /**
     * 分页条件查询
     *
     * @param t      查询条件
     * @param page   当前页码
     * @param size   每页数量
     * @param sortBy 排序条件
     * @return 分页数据
     */
    public PageInfo<T> selectPage(T t, Integer page, Integer size, String sortBy) {
        if (null == page) {
            page = 1;
        }
        if (null == size) {
            size = 10;
        }
        if (StringUtils.isEmpty(sortBy)) {
            sortBy = "";
        }
        PageHelper.startPage(page, size, sortBy);
        List<T> data = this.mapper.select(t);
        return new PageInfo<>(data);
    }

    /**
     * 分页条件查询
     *
     * @param e      查询条件
     * @param page   当前页码
     * @param size   每页数量
     * @param sortBy 排序条件
     * @return 分页数据
     */
    public PageInfo<T> selectPageByExample(Example e, Integer page, Integer size, String sortBy) {
        if (null == page) {
            page = 1;
        }
        if (null == size) {
            size = 10;
        }
        if (StringUtils.isEmpty(sortBy)) {
            sortBy = "";
        }
        PageHelper.startPage(page, size, sortBy);
        List<T> data = this.mapper.selectByExample(e);
        return new PageInfo<>(data);
    }
}