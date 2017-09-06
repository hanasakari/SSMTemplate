package com.swust.zj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swust.zj.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="PageController",description="页面跳转的接口")
@Controller
public class PageController {
	/**
	 * 跳转到主页面
	 * @return
	 */
	@ApiOperation(value="跳转到主页面",notes="跳转到主页面。",httpMethod="GET",response=String.class)
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
