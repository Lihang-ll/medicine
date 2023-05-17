package com.medicine.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.medicine.constant.MedicalConstants;
import com.medicine.dto.CountInfo;
import com.medicine.dto.DoctorInfo;
import com.medicine.entity.*;
import com.medicine.utils.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统跳转控制器
 */
@Controller
public class SystemController extends BaseController<User> {

    /**
     * 首页
     */
    @GetMapping("/index.html")
    public String index(Map<String, Object> map) {
        return "index";
    }

    /**
     * 智能医生
     */
    @GetMapping("/doctor")
    public String doctor(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        return "doctor";
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/index.html";
    }

    /**
     * 所有反馈
     */
    @GetMapping("/all-feedback")
    public String feedback(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        List<Feedback> feedbackList = feedbackService.all();

        map.put("feedbackList", feedbackList);
        return "all-feedback";
    }

    /**
     * 我的资料
     */
    @GetMapping("/profile")
    public String profile(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        return "profile";
    }

    /**
     * 查询相关疾病
     */
    @GetMapping("findIllness")
    public String findIllness(Map<String, Object> map, Integer kind, String illnessName, Integer page) {
        // 处理page
        page = ObjectUtils.isEmpty(page) ? 1 : page;

        Map<String, Object> illness = illnessService.findIllness(kind, illnessName, page);
        if (Assert.notEmpty(kind)) {
            map.put("title", illnessKindService.get(kind).getName() + (illnessName == null ? "" : ('"' + illnessName + '"' + "的搜索结果")));
        } else {
            map.put("title", illnessName == null ? "全部" : ('"' + illnessName + '"' + "的搜索结果"));
        }
        if (loginUser != null && kind != null) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_OPERATE,
                    illnessKindService.get(kind).getId() + "," + (Assert.isEmpty(illnessName) ? "无" : illnessName));
        }
        if (loginUser != null && Assert.notEmpty(illnessName)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illnessName);
        }
        map.putAll(illness);
        map.put("page", page);
        map.put("kind", kind);
        map.put("illnessName", illnessName);
        map.put("kindList", illnessKindService.findList());
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId()));
        return "search-illness";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findIllnessOne")
    public String findIllnessOne(Map<String, Object> map, Integer id) {
        Map<String, Object> illnessOne = illnessService.findIllnessOne(id);
        Illness illness = illnessService.get(id);
        if (loginUser != null) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_ILLNESS, illness.getIllnessName());
        }
        List<Doctor> doctors = doctorService.findByIllness(illness.getIllnessName());
        map.putAll(illnessOne);
        map.put("doctors", doctors);
        return "illness-reviews";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findMedicineOne")
    public String findMedicineOne(Map<String, Object> map, Integer id) {
        Medicine medicine = medicineService.get(id);
//        historyService.insetOne(loginUser.getId(),MedicalConstants.TYPE_MEDICINE,medicine.getMedicineName());
        map.put("medicine", medicine);
        return "medicine";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("findMedicines")
    public String findMedicines(Map<String, Object> map, String nameValue, Integer page) {
        // 处理page
        page = ObjectUtils.isEmpty(page) ? 1 : page;
        if (loginUser != null && Assert.notEmpty(nameValue)) {
            historyService.insetOne(loginUser.getId(), MedicalConstants.TYPE_MEDICINE, nameValue);
        }
        map.putAll(medicineService.getMedicineList(nameValue, page));
        map.put("history", loginUser == null ? null : historyService.findList(loginUser.getId()));
        map.put("title", nameValue);
        return "illness";
    }

    /**
     * 查询相关疾病下的药
     */
    @GetMapping("globalSelect")
    public String globalSelect(Map<String, Object> map, String nameValue) {
        nameValue = nameValue.replace("，", ",");
        List<String> idArr = Arrays.asList(nameValue.split(","));
        //首先根据关键字去查询
        Set<Illness> illnessSet = new HashSet<>();
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_name", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("special_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        idArr.forEach(s -> {
            Illness one = illnessService.getOne(new QueryWrapper<Illness>().like("illness_symptom", s));
            if (ObjectUtil.isNotNull(one)) {
                illnessSet.add(one);
            }
        });
        map.put("illnessSet", illnessSet);
        return "index";
    }

    /**
     * 添加疾病页面
     */
    @GetMapping("add-illness")
    public String addIllness(Integer id, Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        Illness illness = new Illness();
        if (Assert.notEmpty(id)) {
            illness = illnessService.get(id);
        }
        List<IllnessKind> illnessKinds = illnessKindService.all();
        map.put("illness", illness);
        map.put("kinds", illnessKinds);
        return "add-illness";
    }

    /**
     * 添加药品页面
     */
    @GetMapping("add-medical")
    public String addMedical(Integer id, Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        List<Illness> illnesses = illnessService.all();
        Medicine medicine = new Medicine();
        if (Assert.notEmpty(id)) {
            medicine = medicineService.get(id);
            for (Illness illness : illnesses) {
                List<IllnessMedicine> query = illnessMedicineService.query(IllnessMedicine.builder().medicineId(id).illnessId(illness.getId()).build());
                if (Assert.notEmpty(query)) {
                    illness.setIllnessMedicine(query.get(0));
                }
            }
        }
        map.put("illnesses", illnesses);
        map.put("medicine", medicine);
        return "add-medical";
    }

    /**
     * 添加医生页面
     */
    @GetMapping("add-doctor")
    public String addDoctor(Integer id, Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        Doctor doctor = new Doctor();
        if (Assert.notEmpty(id)) {
            doctor = doctorService.get(id);
        }
        map.put("doctor", doctor);
        return "add-doctor";
    }

    /**
     * 疾病管理页面
     */
    @GetMapping("all-illness")
    public String allIllness(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        List<Illness> illnesses = illnessService.all();
        for (Illness illness : illnesses) {
            illness.setKind(illnessKindService.get(illness.getKindId()));
        }
        map.put("illnesses", illnesses);
        return "all-illness";
    }

    /**
     * 药品管理页面
     */
    @GetMapping("all-medical")
    public String allMedical(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        List<Medicine> medicines = medicineService.all();
        map.put("medicines", medicines);
        return "all-medical";
    }

    /**
     * 医生管理页面
     */
    @GetMapping("all-doctor")
    public String allDoctor(Integer page, Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        if (Assert.isEmpty(page) || page < 1) {
            page = 1;
        }
        IPage<Doctor> doctorIPage = doctorService.queryPage(page, 10);
        List<Doctor> doctors = doctorIPage.getRecords();
        long pages = doctorIPage.getPages();
        long current = doctorIPage.getCurrent();
        long total = doctorIPage.getTotal();
        map.put("doctors", doctors);
        map.put("pages", pages);
        map.put("current", current);
        map.put("total", total);
        return "all-doctor";
    }

    @GetMapping("system")
    public String system(Map<String, Object> map) {
        if (Assert.isEmpty(loginUser)) {
            return "redirect:/index.html";
        }
        List<DoctorInfo> doctorInfos = doctorService.doctorInfo();
        List<CountInfo> doctorCount = doctorService.countInfo();
        List<CountInfo> illnessCount = illnessService.countInfo();
        List<CountInfo> medicineCount = medicineService.countInfo();
        map.put("doctorIllnessJson", doctorCount.stream().map(CountInfo::getName)
                .map(dept -> "\"" + dept + "\"") // 添加双引号
                .collect(Collectors.joining(", ", "[", "]")));
        map.put("doctorCountList", doctorCount.stream().map(CountInfo::getCount).collect(Collectors.toList()));
        map.put("illnessJson", illnessCount.stream().map(CountInfo::getName)
                .map(dept -> "\"" + dept + "\"") // 添加双引号
                .collect(Collectors.joining(", ", "[", "]")));
        map.put("illnessCountList", illnessCount.stream().map(CountInfo::getCount).collect(Collectors.toList()));

        map.put("medicineJson", medicineCount.stream().map(CountInfo::getName)
                .map(dept -> "\"" + dept + "\"") // 添加双引号
                .collect(Collectors.joining(", ", "[", "]")));
        map.put("medicineCountList", medicineCount.stream().map(CountInfo::getCount).collect(Collectors.toList()));
        map.put("doctorCount", doctorService.count());
        map.put("illnessCount", illnessService.count());
        map.put("medicineCount", medicineService.count());
        map.put("feedbackCount", feedbackService.count());
        map.put("hotSearch", historyService.hotSearch());
        map.put("goodDoctors", doctorService.goodDoctors());

        map.put("doctorInfoJson", doctorInfos.stream().map(DoctorInfo::getDepartment)
                .map(dept -> "\"" + dept + "\"") // 添加双引号
                .collect(Collectors.joining(", ", "[", "]")));
        map.put("doctorInfoTotalCountList", doctorInfos.stream().map(DoctorInfo::getTotalCount).collect(Collectors.toList()));
        map.put("doctorInfoMainCountList", doctorInfos.stream().map(DoctorInfo::getMainCount).collect(Collectors.toList()));
        map.put("doctorInfoSubCountList", doctorInfos.stream().map(DoctorInfo::getSubCount).collect(Collectors.toList()));
        map.put("doctorInfoMainRateList", doctorInfos.stream().map(DoctorInfo::getMainRate).collect(Collectors.toList()));
        map.put("doctorInfoSubRateList", doctorInfos.stream().map(DoctorInfo::getSubRate).collect(Collectors.toList()));
        map.put("doctorInfoTotalVisitsList", doctorInfos.stream().map(DoctorInfo::getTotalVisits).collect(Collectors.toList()));
        map.put("doctorInfoAverageEvaluationList", doctorInfos.stream().map(DoctorInfo::getAverageEvaluation).collect(Collectors.toList()));
        map.put("doctorInfoFeeList", doctorInfos.stream().map(DoctorInfo::getFee).collect(Collectors.toList()));
        return "system";
    }

}