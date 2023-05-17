package com.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medicine.entity.Pageview;
import org.springframework.stereotype.Repository;

/**
 * 分页数据库访问
 *
 *  
 */
@Repository
public interface PageviewDao extends BaseMapper<Pageview> {

}
