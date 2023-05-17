package com.medicine.controller;

import com.medicine.entity.Doctor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 医生控制器
 *
 *  
 */
@RestController
@RequestMapping("doctors")
public class DoctorController extends BaseController<Doctor> {
}
