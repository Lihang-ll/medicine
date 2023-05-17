package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.dto.CountInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.medicine.entity.Medicine;

import java.util.List;
import java.util.Map;

/**
 * 药品数据库访问
 *
 *  
 */
@Repository
public interface MedicineDao extends BaseMapper<Medicine> {

    /**
     * 根据疾病查询药物
     */
    List<Map<String, Object>> findMedicineList(Integer illnessId);

    @Select("SELECT illness.illness_name name, COUNT(1) as count FROM illness_medicine LEFT JOIN illness ON illness.id = illness_medicine.illness_id\n" +
            "GROUP BY illness_id")
    List<CountInfo> countInfo();
}
