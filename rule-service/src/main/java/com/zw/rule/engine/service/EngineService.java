package com.zw.rule.engine.service;

import com.zw.rule.datamanage.po.Field;
import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.EngineVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineService {
    /**
     * 获取引擎列表
     * @param engine
     * @return
     */
    List<Engine> queryEngineByList(Engine engine);

    /**
     * 通过主键id获取引擎
     * @param engine
     * @return
     */
    Engine queryEngineById(Engine engine);

    /**
     * 通过主键id删除引擎
     * @param engine
     * @return
     */
    int deleteEngine(Engine engine);

    /**
     * 更新引擎
     * @param engine
     * @return
     */
    int updateEngine(Engine engine);

    int addEngine(Engine engine);

    int addEngineAndReturnId(Engine engine);

    /**
     * 保存引擎
     * @param engine
     * @return
     */
    boolean saveEngine(Engine engine , String url);

    String queryEngineExecute(Map<String, Object> map, Engine engine);

    /**
     * 通过字段获取引擎
     * @param engineVersion
     * @param map
     * @return
     */
    List<Field> getEngineByField(EngineVersion engineVersion,Map<String, Object> map);

    /**
     * 获取引擎列表
     * @param organId
     * @param searchString
     * @param list
     * @return
     */
    List<Engine> queryEngineList(@Param("organId") long organId, @Param("searchString") String searchString, @Param("list") List<Integer> list);

    Map<String, Object> getEngineVersionExecute(Map<String, Object> map, String str);

    List<Engine> queryTestEngineListByIds(Map<String, Object> map);
}

