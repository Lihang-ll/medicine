package com.medicine.controller;

import com.medicine.entity.Illness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 疾病控制器
 *
 *  
 */
@RestController
@RequestMapping("illness")
public class IllnessController extends BaseController<Illness> {

}
