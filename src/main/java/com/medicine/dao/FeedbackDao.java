package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.medicine.entity.Feedback;

/**
 * 反馈数据库访问
 *
 *  
 */
@Repository
public interface FeedbackDao extends BaseMapper<Feedback> {

}
