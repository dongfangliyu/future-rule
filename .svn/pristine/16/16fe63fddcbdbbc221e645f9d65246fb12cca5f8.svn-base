package com.zw.rule.mybatis.dao;

import com.zw.rule.base.BaseEntity;
import com.zw.rule.mybatis.page.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T extends BaseEntity> {

    /**
     * 添加对象
     */
    public Serializable save(T entity);

    /**
     * 添加entity
     */
    public Serializable save(String key, Object param);

    /**
     * 删除实体entity
     */
    public void delete(T entity);

    /**
     * 批量删除实体entity
     */
    public void delete(T[] entities);

    /**
     * 根据映射文件中对应的sqlKey以及参数删除数据
     */
    public void delete(String key, Object param);

    /**
     * 返回更新记录数
     */
    public void update(T entity);

    /**
     * 批量更新
     */
    public void update(T[] entity);

    /**
     * 根据获取映射文件中对应的SQL语句更新数据
     */
    public void update(String key, Object param);

    /**
     * 获取单条记录
     */
    public T findUnique(String key, Object param);

    /**
     * 查询单列的值
     */
    public <R> List<R> findColumn(String key, Class<R> returnClass, Object param);

    public <R> R findOneColumn(String key, Class<R> returnClass, Object param);

    /**
     * 查询列表返回List<Map<String,Object>
     */
    public List<Map<String, Object>> findMap(String key);

    public List<Map<String, Object>> findMap(String key, Object param);

    public List<Map<String, Object>> findMap(String key, Object param, Page page);

    /**
     * 根据key获取映射文件中对应的数据
     */
    public List<T> find(String key);

    /**
     * 查询某列的所有值
     */
    public <R> List<R> findColumn(String key, Class<R> returnClass);

    /**
     * 根据分页参数Page获取List集
     */
    public List<T> find(String key, Object param, Page page);

    public List<T> find(String key, Object param);
}
