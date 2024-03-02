package com.weraser.report.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weraser.report.service.HtmlParsingService;

@Controller
public class MainController {
	
	private final HtmlParsingService htmlParsingService;
	
	public MainController(HtmlParsingService htmlParsingService) {
		
        this.htmlParsingService = htmlParsingService;
    }
	
	@GetMapping("/")
    public String index() {

        return "index";
    }
	
	@PostMapping("/htmlAnalyzer")
	@ResponseBody
	public String htmlAnalyzer(@RequestBody Map<String, String>  param) {
		
		var result = htmlParsingService.parseHtml(param.get("url"), Integer.parseInt(param.get("size")), param.get("type"));
		
        return result;
    }
		
}