package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.Engine;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineMapper extends BaseMapper<Engine> {
    List<Engine> getEngineByList(Engine var1);

    Engine getEngineById(Engine var1);

    int updateEngine(Engine var1);

    int deleteEngine(Engine var1);

    int insertEngine(Engine var1);

    int insertEngineAndReturnId(Engine var1);

    Engine getEngineByCode(Engine var1);

    List<Engine> getEngineList(@Param("organId") long var1, @Param("searchString") String var3, @Param("list") List<Integer> var4);

    List<Engine> getTestEngineListByIds(Map<String, Object> var1);
}

