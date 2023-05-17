package com.medicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medicine.dto.CountInfo;
import com.medicine.dto.DoctorInfo;
import com.medicine.entity.Doctor;
import com.medicine.utils.Assert;
import com.medicine.utils.BeanUtil;
import com.medicine.utils.VariableNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicine.dao.DoctorDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 医生服务类
 *
 *  
 */
@Service
public class DoctorService extends BaseService<Doctor> {

    @Autowired
    protected DoctorDao userDao;

    @Override
    public List<Doctor> query(Doctor o) {
        QueryWrapper<Doctor> wrapper = new QueryWrapper();
        if (Assert.notEmpty(o)) {
            Map<String, Object> bean2Map = BeanUtil.bean2Map(o);
            for (String key : bean2Map.keySet()) {
                if (Assert.isEmpty(bean2Map.get(key))) {
                    continue;
                }
                wrapper.eq(VariableNameUtils.humpToLine(key), bean2Map.get(key));
            }
        }
        return userDao.selectList(wrapper);
    }

    @Override
    public List<Doctor> all() {
        return query(null);
    }

    public IPage<Doctor> queryPage(Integer pageNum, Integer pageSize) {
        return userDao.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());
    }

    @Override
    public Doctor save(Doctor o) {
        if (Assert.isEmpty(o.getId())) {
            userDao.insert(o);
        } else {
            userDao.updateById(o);
        }
        return userDao.selectById(o.getId());
    }

    @Override
    public Doctor get(Serializable id) {
        return userDao.selectById(id);
    }

    @Override
    public int delete(Serializable id) {
        return userDao.deleteById(id);
    }

    public List<Doctor> findByIllness(String illnessName) {
        QueryWrapper<Doctor> doctorQueryWrapper = new QueryWrapper<>();
        doctorQueryWrapper.like("info", illnessName);
        return userDao.selectList(doctorQueryWrapper);
    }

    public List<CountInfo> countInfo() {
        return userDao.countInfo();
    }

    public Integer count() {
        return userDao.selectCount(new QueryWrapper<>());
    }

    public List<Doctor> goodDoctors() {
        return userDao.goodDoctors();
    }

    public List<DoctorInfo> doctorInfo() {
        return userDao.doctorInfo();
    }

}