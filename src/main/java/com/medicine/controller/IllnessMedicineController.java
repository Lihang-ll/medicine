package com.medicine.controller;

import com.medicine.entity.IllnessMedicine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 疾病药品控制器
 *
 *  
 */
@RestController
@RequestMapping("illness_medicine")
public class IllnessMedicineController extends BaseController<IllnessMedicine> {

}
