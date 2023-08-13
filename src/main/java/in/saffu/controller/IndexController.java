package in.saffu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.saffu.entity.UserDtlsEntity;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String indexPage()
	{
		return "index";
	}
	
	
}
