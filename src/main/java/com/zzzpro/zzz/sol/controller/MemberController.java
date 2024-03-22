package com.zzzpro.zzz.sol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zzzpro.zzz.sol.dto.MemberDto;
import com.zzzpro.zzz.sol.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
public class MemberController {
	
	/* 컨트롤러는 진짜 컨트롤만! */
	
	@Autowired
	private MemberService mSer;
	
	
	@PostMapping("/member/join")
	public String join(MemberDto mDto, RedirectAttributes ra) {
		mSer.join(mDto, ra);
		return "redirect:/member/loginfrm";
	}
	@GetMapping("/member/loginfrm")
	public String loginfrm(String page, Model mo) {
		mo.addAttribute("page", page);
		return "login_join";
	}
	@PostMapping("/member/login")
	public String login(String page, MemberDto mDto, Model mo, HttpSession session) {
		boolean result = mSer.login(mDto, mo, session);
		if(result) {
			return "redirect:/"+page;
		}
		mo.addAttribute("page", page);
		return "login_join";
	}
	
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("member");
		return "redirect:/main";
	}
	
}
