package com.medicine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医生实体
 *
 *  
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("doctor")
public class Doctor {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 科室
     */
    private String illnessKind;

    /**
     * 真实名字
     */
    private String name;

    /**
     * 职务
     */
    private String job;

    /**
     * 图片的地址
     */
    private String imgPath;

    /**
     * 工作单位
     */
    private String workAddress;

    /**
     * 评分
     */
    private String evaluation;

    /**
     * 擅长
     */
    private String info;

    /**
     * 诊费
     */
    private String visitFee;

    public void trim() {
        this.name = trimString(this.name);
        this.job = trimString(this.job);
        this.workAddress = trimString(this.workAddress);
        this.info = trimString(this.info);
        this.visitFee = trimString(this.visitFee);
        this.illnessKind = trimString(this.illnessKind);
    }

    private String trimString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        // 使用正则表达式去除多余的空格和换行符
        return input.replaceAll("[\\s\\n]+", "").trim();
    }

    public String printInsertSql() {
        StringBuilder sql = new StringBuilder("INSERT INTO doctor (illness_kind, name, job, img_path, work_address, evaluation, info, visit_fee) VALUES (");
        sql.append("'").append(escapeSingleQuotes(this.illnessKind)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.name)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.job)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.imgPath)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.workAddress)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.evaluation)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.info)).append("', ");
        sql.append("'").append(escapeSingleQuotes(this.visitFee)).append("');");
        return sql.toString();
    }

    private String escapeSingleQuotes(String input) {
        if (input == null) {
            return "NULL";
        }
        return input.replace("'", "''");
    }
}
