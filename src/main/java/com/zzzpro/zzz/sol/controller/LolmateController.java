package com.zzzpro.zzz.sol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LolmateController {
	
	@GetMapping("/lolmate/list")
	public String lolmate() {
		return "lolmate";
	}
	
	@GetMapping("/lolmate/write")
	public String lolmateWrite() {
		return "lolmateWrite";
	}
	@GetMapping("/lolmate/detail")
	public String lolmateDetail() {
		return "lolmateDetail";
	}
	
}
