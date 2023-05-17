package com.medicine.controller;

import com.medicine.entity.IllnessKind;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 疾病分类控制器
 *
 *  
 */
@RestController
@RequestMapping("illness_kind")
public class IllnessKindController extends BaseController<IllnessKind> {

}
