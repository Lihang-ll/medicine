package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.dto.CountInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.medicine.entity.History;

import java.util.List;

/**
 * 历史数据库访问
 *
 *  
 */
@Repository
public interface HistoryDao extends BaseMapper<History> {

    @Select("SELECT DISTINCT keyword as name, COUNT(1) as COUNT FROM `history` GROUP BY keyword ORDER BY COUNT(1) DESC LIMIT 20;")
    List<CountInfo> selectHotSearch();
}
