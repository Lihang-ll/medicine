package com.medicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.medicine.dao.FeedbackDao;
import com.medicine.utils.Assert;
import com.medicine.utils.BeanUtil;
import com.medicine.utils.VariableNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicine.entity.Feedback;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 反馈服务类
 *
 *  
 */
@Service
public class FeedbackService extends BaseService<Feedback> {

    @Autowired
    protected FeedbackDao feedbackDao;

    @Override
    public List<Feedback> query(Feedback o) {
        QueryWrapper<Feedback> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return feedbackDao.selectList(wrapper);
    }

    @Override
    public List<Feedback> all() {
        return query(null);
    }

    @Override
    public Feedback save(Feedback o) {
        if (Assert.isEmpty(o.getId())) {
            feedbackDao.insert(o);
        } else {
            feedbackDao.updateById(o);
        }
        return feedbackDao.selectById(o.getId());
    }

    @Override
    public Feedback get(Serializable id) {
        return feedbackDao.selectById(id);
    }

    @Override
    public int delete(Serializable id) {
        return feedbackDao.deleteById(id);
    }

    public Integer count() {
        return feedbackDao.selectCount(new QueryWrapper<Feedback>());
    }
}