package com.medicine.controller;

import com.medicine.entity.History;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 历史控制器
 *
 *  
 */
@RestController
@RequestMapping("history")
public class HistoryController extends BaseController<History> {

}
