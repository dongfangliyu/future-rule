package com.zw.rule.mapper.demo;

import com.zw.rule.demo.pojo.CustomDb;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义名单管理
 * @author zongpeng
 * @Time 2017-6-21
 */
@Repository
public interface CustomDbMapper extends BaseMapper<CustomDb>{

    List<CustomDb> getList(@Param("engineId") Long engineId);

    void add(CustomDb customDb);

    void delete(@Param("id")Long id);

    CustomDb detail(@Param("id")Long id);

    void update(CustomDb customDb);

    int getCount(@Param("name") String name);

    int isCustom(@Param("verId")Long verId);
}
