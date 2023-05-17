package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.dto.CountInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.medicine.entity.Illness;

import java.util.List;

/**
 * 疾病数据库访问
 *
 *  
 */
@Repository
public interface IllnessDao extends BaseMapper<Illness> {

    @Select("SELECT DISTINCT illness_kind.`name` name, COUNT(1) as count FROM illness\n" +
            "LEFT JOIN illness_kind ON illness.kind_id = illness_kind.id\n" +
            "GROUP BY kind_id ORDER BY count DESC LIMIT 20;\n")
    List<CountInfo> countInfo();
}
