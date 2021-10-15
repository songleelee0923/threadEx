package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.RestTask;
import com.example.demo.ThreadExecutor;

@Controller
public class MainController {
	
	 @RequestMapping("/threadTes")
	  public @ResponseBody void rest() {
		 String url = "";
		 
		 ThreadExecutor.getInstance().add(new RestTask(url));
	   
	  }

}
