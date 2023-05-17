package com.medicine.controller;

import com.medicine.entity.Medicine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 药品控制器
 *
 *  
 */
@RestController
@RequestMapping("medicine")
public class MedicineController extends BaseController<Medicine> {

}
