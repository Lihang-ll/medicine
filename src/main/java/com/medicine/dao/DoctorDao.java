package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.dto.CountInfo;
import com.medicine.dto.DoctorInfo;
import com.medicine.entity.Doctor;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医生数据库访问
 *
 *  
 */
@Repository
public interface DoctorDao extends BaseMapper<Doctor> {

    @Select("SELECT DISTINCT illness_kind name, COUNT(1) as count FROM doctor GROUP BY illness_kind ORDER BY count DESC LIMIT 20;")
    List<CountInfo> countInfo();

    @Select("SELECT * \n" +
            "FROM `doctor` \n" +
            "WHERE evaluation != '无评分' \n" +
            "  AND evaluation REGEXP '^[0-9]+(\\.[0-9]+)?$' -- 确保只保留有效的数字\n" +
            "ORDER BY CONVERT(evaluation, DECIMAL(10, 2)) DESC -- 使用 CONVERT 转换为 DECIMAL 类型进行排序\n" +
            "LIMIT 20;")
    List<Doctor> goodDoctors();


    @Select("SELECT\n" +
            "\t\tillness_kind AS department,\n" +
            "\t\tCOUNT(*) AS totalCount,\n" +
            "\t\tSUM( CASE WHEN job = '主任医师' THEN 1 ELSE 0 END ) AS mainCount,\n" +
            "\t\tSUM( CASE WHEN job = '副主任医师' THEN 1 ELSE 0 END ) AS subCount,\n" +
            "\t\tROUND( SUM( CASE WHEN job = '主任医师' THEN 1 ELSE 0 END ) / COUNT(*), 2 ) AS mainRate,\n" +
            "\t\tROUND( SUM( CASE WHEN job = '副主任医师' THEN 1 ELSE 0 END ) / COUNT(*), 2 ) AS subRate,\n" +
            "\t\tSUM( visit_count ) / 10000 AS totalVisits,\n" +
            "\t\tROUND( AVG( CAST( evaluation AS UNSIGNED )), 2 ) AS averageEvaluation,\n" +
            "\t\t\tROUND(\n" +
            "\t\tAVG(\n" +
            "\t\tCASE\n" +
            "\t\t\t\t\n" +
            "\t\t\t\tWHEN visit_fee LIKE '图文问诊%' THEN\n" +
            "\t\t\t\tCAST(\n" +
            "\t\t\t\tSUBSTRING( visit_fee, LOCATE( '￥', visit_fee ) + 1 ) AS DECIMAL ( 10, 2 )) ELSE 0 \n" +
            "\t\t\tEND \n" +
            "\t\t\t),\n" +
            "\t\t\t2 \n" +
            "\t\t) AS fee \n" +
            "\tFROM\n" +
            "\t\tdoctor \n" +
            "\tGROUP BY\n" +
            "\t\tillness_kind \n" +
            "\tORDER BY\n" +
            "\tCOUNT(*) DESC \n" +
            "\tLIMIT 100")
    List<DoctorInfo> doctorInfo();

}
